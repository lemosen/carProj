import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../../../services/member-service/member";
import {NativeProvider} from "../../../../services/native-service/native";

@Component({
    selector: 'app-show-buttons-mode2',
    templateUrl: './show-buttons-mode2.component.html',
    styleUrls: ['./show-buttons-mode2.component.scss']
})
export class ShowButtonsMode2Component implements OnInit {

    constructor(public nativeProvider: NativeProvider, private navCtrl: NavController) {
    }

    ngOnInit() {
    }

    goCouponReceive() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("CouponReceivePage")
        }, 100)
    }

    goFlashPurchase() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("FlashPurchasePage");
        }, 100)
    }

    goSignIn() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("SignInPage");
        }, 100)
    }

    goServiceCenter() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("ServiceCenterPage")
        }, 100)
    }


    goNationalGroupPurchase() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("NationalGroupPurchasePage")
        }, 100)
    }

    goRecommend() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("RecommendPage")
        }, 100)
    }

    goCommunityGrouPurchase() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("CommunityGroupPurchasePage");
        }, 100)
    }

    goCashbackGroupPurchase() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("CashbackGroupPurchasePage");
        }, 100)
    }


    goInvitesCourtesy() {
        if (!MemberProvider.isLogin()) {
            this.nativeProvider.showToastFormI4("请先登录");
            return;
        }
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("InvitesCourtesyPage")
        }, 100)
    }

    goAreaDoubleStar() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("AreaDoubleStarPage");
        }, 100)
    }

    goAreaGift() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("AreaGiftPage");
        }, 100)
    }

}
