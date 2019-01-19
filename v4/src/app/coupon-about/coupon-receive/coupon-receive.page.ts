import {Component} from '@angular/core';
import {NavController} from "@ionic/angular";
import {PageQuery} from "../../../util/page-query.model";
import {CouponService} from "../../../services/coupon-service/coupon.service";
import {NativeProvider} from "../../../services/native-service/native";
import {MemberProvider} from "../../../services/member-service/member";

@Component({
    selector: 'app-coupon-receive',
    templateUrl: './coupon-receive.page.html',
    styleUrls: ['./coupon-receive.page.scss'],
})
export class CouponReceivePage {

    list = [];

    /*子组件对应字段*/
    tickets = [];

    pageQuery: PageQuery = new PageQuery();

    // @ViewChild(CouponComponent) childCoupon: CouponComponent;

    cornerImg = ["", "../../assets/app_icon/customer/角标满@3x.png", "../../assets/app_icon/customer/角标赠@3x.png", "../../assets/app_icon/customer/角标储@3x.png"];
    typeImg = ["", "../../assets/app_icon/customer/满减券标签@3x.png", "../../assets/app_icon/customer/买送券标签@3x.png", "../../assets/app_icon/customer/储值券标签@3x.png"]

    constructor(public navCtrl: NavController, public couponProvider: CouponService, public nativeProvider: NativeProvider) {
    }

    ionViewWillEnter() {
        this.getData(this.pageQuery.requests)
    }

    goCouponReceiveFinish(ticket) {
        if (!MemberProvider.isLogin()) {
            this.navCtrl.navigateForward("LoginPage");
        }
        this.couponProvider.receiveCoupon(ticket.id, MemberProvider.getLoginMember().id).then(e => {
            if (e.result == "SUCCESS") {
                this.navCtrl.navigateForward(["CouponReceiveFinishPage", {couponId: ticket.id}], false)
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        })
    }

    getData(page) {
        if(MemberProvider.isLogin()){
            this.pageQuery.pushParamsRequests("member.id", MemberProvider.getLoginMember().id)
        }

        this.couponProvider.queryCouponReceive(page).then(e => {
            if (e.result == "SUCCESS") {

                this.tickets = e.data.content;
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message)
        })
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();

            this.couponProvider.queryCouponReceive(this.pageQuery.requests).then(e => {
                this.tickets = e.data.content;
                infiniteScroll.target.complete();
            }, err => {
                infiniteScroll.target.complete();
            })
        } else {
            infiniteScroll.target.complete();
        }
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
