import {Component, ViewChild} from '@angular/core';

import {ModalController, NavController} from "@ionic/angular";
import {MemberProvider} from "../../../services/member-service/member";
import {NativeProvider} from "../../../services/native-service/native";
import {CouponReceive} from "../../../domain/original/coupon-receive.model";
import {ActivatedRoute} from "@angular/router";
import {BannerProvider} from "../../../services/banner-service/banner";
import {PageQuery} from "../../../util/page-query.model";

@Component({
    selector: 'app-storage-volume',
    templateUrl: './storage-volume.page.html',
    styleUrls: ['./storage-volume.page.scss'],
})
export class StorageVolumePage {

    storageVolumeVo: CouponReceive[];

    pet = "canuse";

    /*从用户中心进入时,传入true*/
    check: string = 'true';

    /*子组件对应字段*/
    tickets = [];

    advertisingCommodity;

    isLoading:boolean = false;

    pageQuery: PageQuery = new PageQuery();

    constructor(public route: ActivatedRoute, public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController,
        public modalCtrl: ModalController, public bannerProvider: BannerProvider) {

    }

    ionViewWillEnter() {
        this.listFilter(this.pet);
        this.getAdvertisingCommodityData();
    }

    listFilter(type) {
        this.pageQuery.resetRequests();
        this.pageQuery.addFilter('member.id', MemberProvider.getLoginMember().id, 'eq');
        this.pageQuery.addFilter('couponType', 2, 'eq');
        this.queryFilter(type);
        this.getData(this.pageQuery);
    }

    queryFilter(type) {
        switch (type) {
            case "canuse":
                this.pageQuery.addFilter('state', 1, 'eq');
                break;
            case "unuse":
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
            this.storageVolumeVo = data.data.content;
            this.pageQuery.covertResponses(data.data);
            this.tickets = this.transform(this.storageVolumeVo);
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }
    /*广告*/
    getAdvertisingCommodityData() {
        this.bannerProvider.getAdvertisingCommodity(6).then( data => {
            if(data.result == "SUCCESS") {
                this.advertisingCommodity = data.data;
            }else {
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
                state: ["", "storage-canuse", "storage-unuse", "storage-unuse"][e.state],
                parValue: e.parValue,
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
