import {Component} from '@angular/core';
import {AlertController, Events, NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {REFRESH_CUSTOMERCENTER, SUCCESS} from "../Constants";
import {NativeProvider} from "../../services/native-service/native";
import {Observable} from "rxjs/internal/Observable";
import {OrderProvider} from "../../services/order-service/order";

@Component({
    selector: 'app-customer-center',
    templateUrl: './customer-center.page.html',
    styleUrls: ['./customer-center.page.scss'],
})
export class CustomerCenterPage {
    member;
    localCity: string;

    orderNum = [0,0,0,0];
    constructor(public orderProvider: OrderProvider, public alertCtrl: AlertController, public nativeProvider: NativeProvider, public navCtrl: NavController, public events: Events, public memberProvider: MemberProvider) {
        this.events.subscribe(REFRESH_CUSTOMERCENTER, () => this.ionViewWillEnter())
    }

    ionViewWillEnter() {
        this.orderNum = [0,0,0,0];
        this.localCity = this.memberProvider.getLocationInfo();
        this.member = MemberProvider.getLoginMember();
        if(MemberProvider.isLogin()){
            this.orderProvider.getOrdersNum(MemberProvider.getLoginMember().id).then( e => {
                if(e.result == "SUCCESS"){
                    this.orderNum = e.data;
                }else{
                    this.nativeProvider.showToastFormI4(e.message);
                }
            }, err => this.nativeProvider.showToastFormI4(err.message));

            this.memberProvider.getMember(MemberProvider.getLoginMember().id).then( e => {
                if (e.result==SUCCESS){
                    this.memberProvider.setLoginMember(e.data);
                    this.member = e.data;
                }
            })
        }
    }

    isLogin() {
        return new Promise((resolve, reject) => {
            if (MemberProvider.isLogin()) {
                resolve();
            } else {
                this.navCtrl.navigateForward("LoginPage");
                return;
            }
        });
    }

    goPersonalCenter() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("PersonalCenterPage")
        });
    }

    goMycode() {
        this.navCtrl.navigateForward("MyCodePage")
    }

    goSystemSet() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("SystemSetPage")
        });
    }

    goCoupon() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("CouponPage")
        });
    }

    goInvitesCourtesy() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("InvitesCourtesyPage")
        });
    }

    goMyTeam() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("MyTeamPage")
        });
    }

    goMyBalance() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("MyBalancePage")
        });
    }


    goFineBalance() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("FineBalancePage")
        });
    }

    goMyOrderPage(state?) {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward(["MyOrderPage", {state: state}])
        });
    }

    goAfterSales() {
        this.isLogin().then(e => {
            this.navCtrl.navigateForward("AfterSalesPage")
        });
    }

    goServiceCenter() {
        this.navCtrl.navigateForward("ServiceCenterPage");
    }
    goCart() {
        this.navCtrl.navigateForward("ShoppingCartPage");
    }


    goLogin() {
        if (MemberProvider.isLogin()) {
            this.goPersonalCenter();
        } else {
            this.navCtrl.navigateForward("LoginPage")
        }
    }


}
