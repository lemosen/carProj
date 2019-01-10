import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";
import {Params} from "@angular/router";

@Component({
    selector: 'app-coupon-modal',
    templateUrl: './coupon-modal.page.html',
    styleUrls: ['./coupon-modal.page.scss'],
})
export class CouponModalPage implements OnInit {

    selectedCoupon;

    coupons;

    type;

    constructor(public modalCtrl: ModalController, public params: NavParams) {
        this.coupons = this.params.data.couponList;
        this.selectedCoupon = this.params.data.coupon;
        this.type = this.params.data.type;
    }

    ngOnInit() {

    }

    Selected(item) {
        this.selectedCoupon = item;
        this.goBack()
    }

    cancel() {
        this.selectedCoupon = undefined;
        this.goBack()
    }

    goBack() {
        this.modalCtrl.dismiss(this.selectedCoupon);
    }

}
