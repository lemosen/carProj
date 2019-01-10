import {Component, OnInit, ViewChild} from '@angular/core';
import {ModalController, NavController, NavParams} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {CouponReceive} from "../../../domain/original/coupon-receive.model";
import {MemberProvider} from "../../../services/member-service/member";
import {NativeProvider} from "../../../services/native-service/native";
import {TicketComponent} from "../../components/ticket/ticket.component";

@Component({
  selector: 'app-storage-volume-order',
  templateUrl: './storage-volume-order.page.html',
  styleUrls: ['./storage-volume-order.page.scss'],
})
export class StorageVolumeOrderPage {

    storageVolumeVo: CouponReceive[] = [];

    pet = "canuse";

    /*子组件对应字段*/
    tickets = [];

    /*购物车已选储值券id*/
    checkedId:number;

    constructor(public route: ActivatedRoute, public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController,
        public modalCtrl: ModalController, public navParams: NavParams) {
        if(this.navParams.data.storage){
            this.checkedId=this.navParams.data.storage.id;
        }

    }

    ionViewWillEnter() {
        // this.listFilter("canuse");
        // this.memberProvider.getCoupon(MemberProvider.getLoginMember().id).then(e => {
        //     this.storageVolumeVo = e.data.filter(e => e.coupon.couponType == 3);
        //
        //     this.tickets = this.storageVolumeVo.map(e => {
        //         return {
        //             id: e.id,
        //             state: ["", "storage-canuse", "storage-unuse", "storage-unuse"][e.state],
        //             parValue: e.parValue,
        //             useConditionType: e.coupon.useConditionType,
        //             fullMoney: e.coupon.fullMoney,
        //             fullQuantity: e.coupon.fullQuantity,
        //             platform: "",
        //             endTime: e.endTime,
        //             couponType: e.coupon.couponType
        //         }
        //     });
        //
        //     this.listFilter("canuse");
        // }, err => {
        //     this.nativeProvider.showToastFormI4(err.message);
        // });
    }

    /*显示列表*/
    list = [];

    listFilter(type) {
        switch (type) {
            case "canuse":
                this.list = this.tickets.filter(e =>
                    e.state == "storage-canuse"
                );
                break;
            case "unuse":
                this.list = this.tickets.filter(e =>
                    e.state == "storage-unuse"
                );
                break;
            default:
                this.list = this.tickets;
                break;
        }
    }


    /*获取子组件选中的券*/
    @ViewChild(TicketComponent) childTicket: TicketComponent;
    selectedCoupon;

    goWriteOrder() {
        if (this.childTicket) {

            /*cancel即ticket组件取消check选择返回，后面为组件ticket选中返回的正确优惠券，nav则是没有任何操作由返回原本的优惠券*/
            this.selectedCoupon = (this.childTicket.selectedCoupon == "cancel" ? undefined : (this.childTicket.selectedCoupon || this.navParams.data.storage));

        }
        if (this.selectedCoupon) {
            this.modalCtrl.dismiss(this.selectedCoupon);
        } else {
            this.modalCtrl.dismiss();
        }
    }

}
