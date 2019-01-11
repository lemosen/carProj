import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";

@Component({
    selector: 'app-service-modal',
    templateUrl: './service-modal.page.html',
    styleUrls: ['./service-modal.page.scss'],
})
export class ServiceModalPage implements OnInit {

    list = [{
        subtitle: "售后退款",
        content: "自预约成功但未报考成功这个时间段内，实时可选择进行退款售后，但一旦缴纳报考所有费用后，合同及时生效，此时则不支持售后退款"
    }, {
        subtitle: "红包使用",
        content: "在预约支付时勾选红包，此红包在预约时不起作用，但在预约成功后，二期线下缴纳报考费用时可做抵扣，红包金额=抵扣金额。如500元红包，报考需要5000元，此时则可抵扣500元，缴纳时只需缴纳4500元即可"
    }];

    constructor(public navParams: NavParams, public modalCtrl: ModalController) {

    }

    ngOnInit() {
    }

    goBack() {
        this.modalCtrl.dismiss();
    }

}
