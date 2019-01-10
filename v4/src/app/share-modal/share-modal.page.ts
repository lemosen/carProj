import {Component, OnInit} from '@angular/core';
import {ModalController} from "@ionic/angular";
import {ShareClickModalPage} from "./share-click-modal/share-click-modal.page";

@Component({
    selector: 'app-share-modal',
    templateUrl: './share-modal.page.html',
    styleUrls: ['./share-modal.page.scss'],
})
export class ShareModalPage implements OnInit {

    constructor(public modalCtrl: ModalController) {
    }

    ngOnInit() {
    }

    async openShareClickModal() {
        this.modalCtrl.dismiss();
        const modal = await this.modalCtrl.create({
                component: ShareClickModalPage,
            }
        );
        await modal.present();
    }
}
