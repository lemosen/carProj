import {Component, OnInit} from '@angular/core';
import {NativeProvider} from "../../../services/native-service/native";
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../../services/member-service/member";
import {CouponReceive} from "../../../domain/original/coupon-receive.model";
import {BannerProvider} from "../../../services/banner-service/banner";
import {PageQuery} from "../../../util/page-query.model";

@Component({
    selector: 'app-coupon',
    templateUrl: './coupon.page.html',
    styleUrls: ['./coupon.page.scss'],
})
export class CouponPage implements OnInit {

    pet = "canuse";

    /*从用户中心进入时传入true*/
    check: string = 'true';
    couponVo: CouponReceive[] = [];

    /*子组件字段对应字段*/
    tickets = [];

    advertisingCommodity;

    isLoading: boolean = false;

    pageQuery: PageQuery = new PageQuery();

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController, public bannerProvider: BannerProvider) {

    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.listFilter(this.pet);
        this.getAdvertisingCommodityData();
    }

    listFilter(type) {
        this.pageQuery.resetRequests();
        this.pageQuery.addFilter('member.id', MemberProvider.getLoginMember().id, 'eq');
        this.pageQuery.addFilter('couponType', 2, 'lte');
        this.queryFilter(type);
        this.getData(this.pageQuery);
    }

    queryFilter(type) {
        switch (type) {
            case "canuse":
                this.pageQuery.addFilter('state', 1, 'eq');
                break;
            case "already":
                this.pageQuery.addFilter('state', 2, 'eq');
                break;
            case "expired":
                this.pageQuery.addFilter('state', 3, 'eq');
                break;
            default:
                break;
        }
    }

    private getData(page: PageQuery) {
        this.tickets = [];
        this.isLoading = true;
        this.memberProvider.queryCoupon(page.requests).then(data => {
            this.couponVo = data.data.content;
            this.pageQuery.covertResponses(data.data);
            this.tickets = this.transform(this.couponVo);
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    /*广告*/
    getAdvertisingCommodityData() {
        this.bannerProvider.getAdvertisingCommodity(6).then(data => {
            if (data.result == "SUCCESS") {
                this.advertisingCommodity = data.data;
            } else {
                this.nativeProvider.showToastFormI4(data.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.memberProvider.queryCoupon(this.pageQuery.requests).then(data => {
                    this.tickets = this.tickets.concat(this.transform(data.data.content));
                    this.pageQuery.covertResponses(data.data);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

    transform(couponVo) {
        return couponVo.map(e => {
            return {
                id: e.id,
                state: ["", "coupon-canuse", "coupon-already", "coupon-expired"][e.state],
                parValue: e.coupon.parValue,
                useConditionType: e.coupon.useConditionType,
                fullMoney: e.coupon.fullMoney,
                fullQuantity: e.coupon.fullQuantity,
                validDays: e.validDays,
                endTime: e.endTime,
                couponType: e.coupon.couponType
            };
        });
    }

}
