import {Component, OnInit} from '@angular/core';
import {ModalController, NavParams} from "@ionic/angular";
import {AttrGroupModalPage} from "../attr-group-modal/attr-group-modal.page";

@Component({
    selector: 'app-group-purchase-modal',
    templateUrl: './group-purchase-modal.page.html',
    styleUrls: ['./group-purchase-modal.page.scss'],
})
export class GroupPurchaseModalPage implements OnInit {

    groupInfo;

    groupMember = [];

    constructor(public modalCtrl: ModalController, public navParam: NavParams) {
        this.groupInfo = this.navParam.data;
        this.groupMember = this.navParam.data.nationalGroupRecords;
    }

    ngOnInit() {
    }

    async joinGroup(item) {
        this.modalCtrl.dismiss();

        /*拼团活动*/
        const modal = await this.modalCtrl.create({
            component: AttrGroupModalPage,
            componentProps: {data: this.groupInfo, groupId:item.id, orderType: 'joinGroup'}
        });
        await modal.present()
    }

}
