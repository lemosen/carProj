import {Component} from '@angular/core';
import {ShippingAddress} from "../../domain/original/shipping-address.model";
import {SaleOrder} from "../../domain/original/sale-order.model";
import {MemberProvider} from "../../services/member-service/member";
import {OrderProvider} from "../../services/order-service/order";
import {ModalController, NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {NativeProvider} from "../../services/native-service/native";
import {ConsigneeModalPage} from "../consignee-modal/consignee-modal.page";
import {Coupon} from "../../domain/original/coupon.model";
import {CouponModalPage} from "../coupon-about/coupon-modal/coupon-modal.page";

@Component({
    selector: 'app-write-order',
    templateUrl: './write-order.page.html',
    styleUrls: ['./write-order.page.scss'],
})
export class WriteOrderPage {
    orderProductVos: SaleOrder;
    shippingAddressVo: ShippingAddress;

    /*订单类型，普通订单undefined, openGroup,joinGroup*/
    orderType;

    ordersDetail;

    constructor(public memberProvider: MemberProvider, public orderProvider: OrderProvider, public navCtrl: NavController, public modalCtrl: ModalController,
                public router: ActivatedRoute, public nativeProvider: NativeProvider) {
        if (this.router.params['value'].orderType) {
            this.orderType = this.router.params['value'].orderType
        }
    }

    ionViewWillEnter() {
        if (!this.orderType) {
            this.getOrder('confirmOrder', this.router.params['value'].shoppingCart);
        }
        if (this.orderType == "openGroup" || this.orderType == "joinGroup") {
            this.getOrder('confirmGroupOrder', this.router.params['value'].data);
        }
    }

    async goAddConsignee() {
        const modalAddConsignee = await this.modalCtrl.create({
            component: ConsigneeModalPage,
            componentProps: {isOrder: true},
        });
        await modalAddConsignee.present();
        await modalAddConsignee.onDidDismiss().then(data => {
            if (data.data != undefined) {
                this.shippingAddressVo = data.data;
                this.ordersDetail.shippingAddress = data.data;
                if (!this.orderType) {
                    this.getOrder('confirmOrder', this.ordersDetail);
                }
            }
        })
    }

    async goChooseConsignee() {

    }

    //使用的优惠券信息
    coupon: Coupon[] = [];
    storage: Coupon[] = [];

    async goMyCoupon(couponList, i) {
        if (!couponList) {
            return
        }
        const modalCouponOrder = await this.modalCtrl.create({
            component: CouponModalPage,
            componentProps: {couponList: couponList, coupon: this.coupon[i], type: "coupon"}
        });
        await modalCouponOrder.present();
        await modalCouponOrder.onDidDismiss().then(data => {
            this.ordersDetail.splitOrders[i].couponAmount = data.data ? data.data.parValue : 0;
            this.coupon[i] = data.data;

            this.count(i);
        })
    }

    async goMyStorage(couponList, i) {
        if (!couponList) {
            return
        }
        const modalCouponOrder = await this.modalCtrl.create({
            component: CouponModalPage,
            componentProps: {couponList: couponList, coupon: this.storage[i], type: "storage"}
        });
        await modalCouponOrder.present();
        await modalCouponOrder.onDidDismiss().then(data => {
            this.ordersDetail.splitOrders[i].voucherAmount = data.data ? data.data.parValue : 0;
            this.storage[i] = data.data;

            this.count(i)
        })
    }


    count(i) {
        let splitOrders = [];
        splitOrders[i] = this.ordersDetail.splitOrders[i];

        splitOrders[i].payAmount = splitOrders[i].saleOrderItems.map(e => {
            return e.price * e.quantity;
        }).reduce((a, b) => {
            return a + b
        }) + splitOrders[i].freight - splitOrders[i].couponAmount - splitOrders[i].voucherAmount;

        splitOrders[i].payAmount = Number(splitOrders[i].payAmount.toFixed(2));
        this.ordersDetail.splitOrders[i] = splitOrders[i];

        this.ordersDetail.payAmount = this.ordersDetail.splitOrders.map(e => {
            return e.payAmount
        }).reduce((a, b) => {
            return a + b
        })
    }

    //防止重复提交
    private submitFlag = false;

    goOrderPay() {
        if (this.submitFlag) {
            this.nativeProvider.showToastFormI4("正在提交中");
            return;
        }
        this.submitFlag = true;

        this.ordersDetail.splitOrders.forEach((e, i) => {
            e.coupons = [];
            e.vouchers = [];
            if (this.coupon[i]) {
                e.coupons.push(this.coupon[i])
            }
            if (this.storage[i]) {
                e.vouchers.push(this.storage[i])
            }
        });

        let saleOrderBo = {
            member: {id: MemberProvider.getLoginMember().id},
            shippingAddress: {id: this.shippingAddressVo.id},
            splitOrders: this.ordersDetail.splitOrders,
            cartVos: this.ordersDetail.cartVos,
            groupType: this.ordersDetail.groupType,
            orderType: this.ordersDetail.orderType,
            groupId: this.ordersDetail.groupId,
            nationalGroupRecordId: this.ordersDetail.nationalGroupRecordId,
            orderSource:1,
        };

        if (!this.orderType) {
            this.submitOrder('submitOrder', saleOrderBo);
        }
        if (this.orderType == "openGroup" || this.orderType == "joinGroup") {
            this.submitOrder('submitGroupOrder', saleOrderBo);
        }

    }

    /**
     *
     * @param type submitOrder 普通订单 submitGroupOrder 拼团订单
     * @param saleOrderBo
     */
    submitOrder(type, saleOrderBo) {
        this.orderProvider[type](saleOrderBo).then(e => {
                let ids = [];
                if (e.result == "SUCCESS") {
                    e.data.splitOrders.forEach(e => {
                        ids.push(e.id);
                    });
                    this.navCtrl.navigateForward(["PaymentOrderPage", {ids: ids, orderType: this.orderType}]);
                    this.submitFlag = false;
                } else {
                    this.nativeProvider.showToastFormI4(e.message);
                    this.submitFlag = false;
                }
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
                this.submitFlag = false;
            }
        )
    }

    /**
     *
     * @param type confirmOrder addGroupOrder 普通 开团
     * @param data
     */
    getOrder(type, data) {
        this.orderProvider[type](data).then(e => {
            if (e.result == "SUCCESS") {
                this.ordersDetail = e.data;

                this.shippingAddressVo = this.ordersDetail.shippingAddress;
                this.ordersDetail.splitOrders.forEach((e, i) => {
                    e.productList = this.nativeProvider.productListChange(e.saleOrderItems);
                    if (e.coupons) {
                        this.coupon[i] = e.coupons[0];
                    }
                    if (e.vouchers) {
                        this.storage[i] = e.vouchers[0];
                    }
                })
            } else {
                this.nativeProvider.showToastFormI4(e.message);
                setTimeout(() => {
                    this.navCtrl.goBack();
                }, 1000)
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            setTimeout(() => {
                this.navCtrl.goBack();
            }, 1000)
        })
    }


}
