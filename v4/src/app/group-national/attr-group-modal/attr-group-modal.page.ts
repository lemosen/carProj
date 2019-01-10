import {Component, OnInit} from '@angular/core';
import {ModalController, NavController, NavParams} from "@ionic/angular";
import {MemberProvider} from "../../../services/member-service/member";
import {OrderProvider} from "../../../services/order-service/order";
import {NativeProvider} from "../../../services/native-service/native";

@Component({
    selector: 'app-attr-group-modal',
    templateUrl: './attr-group-modal.page.html',
    styleUrls: ['./attr-group-modal.page.scss'],
})
export class AttrGroupModalPage implements OnInit {

    groupInfo;
    /*数量*/
    amount: number = 1;

    /*订单类型，openGroup,joinGroup*/
    orderType;

    constructor(public navParam: NavParams, public modalCtrl: ModalController, public orderProvider: OrderProvider, public nativeProvider: NativeProvider,
                public navCtrl: NavController) {
        this.orderType = this.navParam.data.orderType;
        this.groupInfo = this.navParam.data.data;
    }

    ngOnInit() {
    }

    reduce() {
        if (this.amount > 1) {
            this.amount--;
        }
    }

    add() {
        this.amount++;
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

    /**
     * orderType 0默认订单 1开团，参团
     * groupType 拼团类型（1.全国拼团 2.小区拼团 3.返现拼团 4.秒杀活动）
     * groupId 拼团配置ID
     * nationalGroupRecordId 0普通订单和开团 id参团时为开团
     */
    goShoppingCart() {
        let shoppingCartBo = {
            member: {id: MemberProvider.getLoginMember().id},
            groupType: "1",
            orderType: "",
            groupId:this.groupInfo.id,
            cartVos:[],
            nationalGroupRecordId: "",
        };

        if(this.orderType == "openGroup"){
            shoppingCartBo.orderType = "1";
            shoppingCartBo.cartVos = [{product: {id: this.groupInfo.product.id}, quantity: this.amount}];
        }else{
            shoppingCartBo.orderType = "2";
            shoppingCartBo.nationalGroupRecordId=this.navParam.data.groupId;
            shoppingCartBo.cartVos = [{product: {id: this.groupInfo.product.id}, quantity: this.amount}]
        }

        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward("LoginPage")
        } else {
            this.modalCtrl.dismiss();
            this.navCtrl.navigateForward(["WriteOrderPage", {data: JSON.stringify(shoppingCartBo), orderType: this.orderType}], false);

            /*this.orderProvider.addGroupOrder(shoppingCartBo).then(e => {
                if (e.result == "SUCCESS") {
                    this.modalCtrl.dismiss();
                    this.navCtrl.navigateForward(["WriteOrderPage", {id: e.data.id, orderType: this.orderType}], false);
                } else {
                    this.nativeProvider.showToastFormI4(e.message)
                }
            }, err => this.nativeProvider.showToastFormI4(err.message))*/
        }
    }

}
