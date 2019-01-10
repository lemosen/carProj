import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {SUCCESS} from "../Constants";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-member-grade',
    templateUrl: './member-grade.page.html',
    styleUrls: ['./member-grade.page.scss'],
})
export class MemberGradePage implements OnInit {

    data;

    constructor(public navCtrl: NavController, public memberProvider: MemberProvider, public nativeProvider: NativeProvider) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.memberProvider.getAccountInfo(MemberProvider.getLoginMember().id).then(e => {
            if (e.result == SUCCESS) {
                this.data = e.data;
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    goGradeRule() {
        this.navCtrl.navigateForward("GradeRulePage");
    }

    goGrowthValue() {
        this.navCtrl.navigateForward("GrowthValuePage");
    }

}
