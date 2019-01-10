import {Component, ViewChild} from '@angular/core';
import {ModalController, NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {GroupShareModalPage} from "../group-national/group-share-modal/group-share-modal.page";
import {TimeCountComponent} from "../components/time-count/time-count.component";
import {OrderProvider} from "../../services/order-service/order";
import {Order} from "../../domain/original/order.model";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-payment-finish',
    templateUrl: './payment-finish.page.html',
    styleUrls: ['./payment-finish.page.scss'],
})
export class PaymentFinishPage {

    list = ["壹壹优选不会以订单异常、系统升级为由要求您点击任何网址链接进行退款操作。"];

    totalAmount;
    back = true;

    /*订单类型，普通订单undefined, openGroup,joinGroup*/
    orderType;

    order:Order;

    @ViewChild('timeCount') timeCount: TimeCountComponent;

    constructor(public route: ActivatedRoute, public navCtrl: NavController, public router: ActivatedRoute, public modalCtrl: ModalController,
    public orderProvider: OrderProvider, public nativeProvider: NativeProvider) {

        if (this.router.params['value'].orderType) {
            this.orderType = this.router.params['value'].orderType
        }
    }

    ionViewWillEnter() {
        this.totalAmount = this.route.params["value"].totalAmount;

        /*if(this.orderType == 'openGroup'){
            this.orderProvider.getOrder(this.totalAmount = this.route.params["value"].ids).then(e1 => {
                if (e1.result == "SUCCESS") {
                    this.order = e1.data;
                    this.timeCount.setLeaveTimeHMS(this.order.nationalGroupRecord.endTime);
                } else {
                    this.nativeProvider.showToastFormI4(e1.message);
                }
            }, err => this.nativeProvider.showToastFormI4(err.message));
        }*/
    }


    goHome() {
        this.navCtrl.navigateForward("/tabs/(home:home)");
    }

    goOrderDetail() {
        this.navCtrl.navigateForward("OrderDetailPage");
    }

    goDeliverMyOrder() {
        this.navCtrl.navigateForward(["MyOrderPage", {state: "deliver", goBackPage:"customerCenter"}])
    }


    async inviteFriends(){
        const modal = await this.modalCtrl.create({
            component: GroupShareModalPage,
        });
        await modal.present();
    }

}
