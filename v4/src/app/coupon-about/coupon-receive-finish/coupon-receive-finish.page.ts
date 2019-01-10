import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavController} from "@ionic/angular";
import {CouponService} from "../../../services/coupon-service/coupon.service";

@Component({
    selector: 'app-coupon-receive-finish',
    templateUrl: './coupon-receive-finish.page.html',
    styleUrls: ['./coupon-receive-finish.page.scss'],
})
export class CouponReceiveFinishPage {

    item;

    cornerImg = ["", "../../assets/app_icon/customer/角标满@3x.png", "../../assets/app_icon/customer/角标赠@3x.png", "../../assets/app_icon/customer/角标储@3x.png"];
    typeImg = ["", "../../assets/app_icon/customer/满减券标签@3x.png", "../../assets/app_icon/customer/买送券标签@3x.png", "../../assets/app_icon/customer/储值券标签@3x.png"]

    list = ["您可登陆账户并在【我的】>【优惠券】处查看获得的优惠券", "本优惠券不得转让", "优惠券最终解释权归壹壹平台所有"];

    constructor(public navCtrl: NavController, public route: ActivatedRoute, public couponProvider: CouponService) {

    }

    ionViewWillEnter() {
        this.couponProvider.getCoupon(this.route.params["value"].couponId).then(e => {
            this.item = e.data
        })
    }

    goHome() {
        this.navCtrl.navigateForward("")
    }

    /*使用条件提示*/
    getConditionContent(useConditionType, fullMoney, fullQuantity) {
        switch (useConditionType) {
            case 0 :
                return "&nbsp;";
            case 1 :
                return "满" + fullMoney + "元可用";
            case 2 :
                return "满" + fullQuantity + "件可用";
            default:
                return "&nbsp;"
        }
    }

    /*有效时间提示*/
    getTerm(endTime, fixedDay) {
        if (endTime) {
            return "有效期至：" + endTime.substr(0, 10);
        }
        if (fixedDay) {
            return "有效天数：" + fixedDay + "天";
        }
    }
}
