import {Component, Input, OnInit} from '@angular/core';
import {ModalController, NavController} from "@ionic/angular";

@Component({
    selector: 'goback',
    templateUrl: './goback-btn.component.html',
    styleUrls: ['./goback-btn.component.scss']
})
export class GobackBtnComponent implements OnInit {

    @Input()
    isModal: boolean=false;

    @Input()
    isShare: boolean=false;

    @Input()
    goBackPage: string;

    constructor(public navCtrl: NavController, public ModalCtrl: ModalController) {
    }

    ngOnInit() {
    }

    goBack() {
        if (this.isModal) {
            this.ModalCtrl.dismiss();
        } else if (this.isShare) {
            window.location.href = window.location.href.split('#')[0] + '#/tabs/(home:home)'
        } else if(this.goBackPage == "customerCenter"){
            this.navCtrl.navigateRoot("/tabs/(customerCenter:customerCenter)");
        } else {
            this.navCtrl.goBack(false);
        }
    }
}
