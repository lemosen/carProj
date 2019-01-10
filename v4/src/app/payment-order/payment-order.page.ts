import {Component, ViewChild} from '@angular/core';
import {PaymentModel} from "../../domain/original/PaymentModel";
import {Order} from "../../domain/original/order.model";
import {AlertController, Events, NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {OrderProvider} from "../../services/order-service/order";
import {NativeProvider} from "../../services/native-service/native";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {MemberProvider} from "../../services/member-service/member";
import {REFRESH_SHOPPINGCART} from "../Constants";
import {TimeCountComponent} from "../components/time-count/time-count.component";

@Component({
    selector: 'app-payment-order',
    templateUrl: './payment-order.page.html',
    styleUrls: ['./payment-order.page.scss'],
})
export class PaymentOrderPage {

    paymentModel: PaymentModel = new PaymentModel();

    orders: Order[] = [];

    /*应付总金额*/
    totalAmount: number = 0;

    /*订单类型，普通订单undefined, openGroup,joinGroup,flash*/
    orderType;

    ids = '';

    @ViewChild('timeCount') timeCount: TimeCountComponent;

    constructor( public navCtrl: NavController, public events: Events, public route: ActivatedRoute, public orderProvider: OrderProvider,
                public nativeProvider: NativeProvider, public router: ActivatedRoute, public weChatProvider: WechatService, public alertController: AlertController) {
        if (this.router.params['value'].orderType) {
            this.orderType = this.router.params['value'].orderType
        }


    }

    ionViewWillEnter() {
        this.ids = this.route.params["value"].ids;

        this.ids.split(',').forEach( e => {
            this.orderProvider.getOrder(e).then(e1 => {
                if (e1.result == "SUCCESS") {
                    this.orders.push(e1.data);
                    this.totalAmount += e1.data.payAmount;
                    this.timeCount.setEndTime(e1.data.payInvalidTime);
                } else {
                    this.nativeProvider.showToastFormI4(e1.message);
                }
            }, err => this.nativeProvider.showToastFormI4(err.message));
        });
    }


    //防止重复提交
    private submitFlag = false;
    goWechatPay() {
        if (this.submitFlag) {
            this.nativeProvider.showToastFormI4("正在提交中");
            return;
        }
        this.submitFlag = true;

        let weChatVo = {
            memberId: MemberProvider.getLoginMember().id,
            orderIds: this.ids,
            openId: MemberProvider.getLoginMember().openId,
            totalFee: this.totalAmount
        };

        // this.navCtrl.navigateForward(["PaymentFinishPage", {totalAmount: this.totalAmount, orderType: this.orderType, orderId: this.ids}])
        this.weChatProvider.readyPay(weChatVo).then(e => {
            this.submitFlag = false;
            if (e.result == "SUCCESS") {
                this.weChatProvider.pay(e.data, () => {
                    this.navCtrl.navigateForward(["PaymentFinishPage", {totalAmount: this.totalAmount, ids: this.ids, orderType: this.orderType}])
                }, () => {
                    this.navCtrl.navigateForward(["PaymentFailPage", {totalAmount: this.totalAmount, ids: this.ids, orderType: this.orderType}])
                }, () => {
                    this.navCtrl.navigateForward(["PaymentFailPage", {totalAmount: this.totalAmount, ids: this.ids, orderType: this.orderType}])
                })
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.submitFlag = false;
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    async goBack() {
        const alert = await this.alertController.create({
            header: '确认要离开订单支付？',
            message: '超过支付时效时订单将被取消，请尽快完成支付。',
            buttons: [
                {
                    text: '继续支付',
                    role: 'cancel',
                    handler: () => {
                    }
                }, {
                    text: '确认离开',
                    handler: () => {
                        this.events.publish(REFRESH_SHOPPINGCART);
                        this.navCtrl.navigateRoot("/tabs/(shoppingCart:shoppingCart)");
                    }
                }
            ]
        });
        await alert.present()
    }
}
