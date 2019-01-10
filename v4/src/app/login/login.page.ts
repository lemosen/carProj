import {Component} from '@angular/core';
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {ShareData} from "../../domain/original/share-data.model";
import {ActivatedRoute} from "@angular/router";
import {inputHandle} from "../../util/bug-util";

@Component({
    selector: 'app-login',
    templateUrl: './login.page.html',
    styleUrls: ['./login.page.scss'],
})
export class LoginPage {
    href = window.location.href;

    /**
     * 分享参数(包含分享人id和用户wechat参数
     */
    shareData: string;

    constructor(public wechatService: WechatService, public memberProvider: MemberProvider, public navCtrl: NavController, public route: ActivatedRoute) {
    }

    ngOnInit() {
        this.shareData = this.route.params["value"].shareData;
        inputHandle();
    }


    goForgetPassword() {
        this.navCtrl.navigateForward("ForgetPasswordPage")
    }

    goBack() {
        this.navCtrl.goBack(false)
    }

    weChatLogin() {
        if (this.shareData == 'null' || !this.shareData) {
            this.wechatService.getWechatId("weChatLogin");
            return
        }
        this.navCtrl.navigateForward(["RegisterPage", {shareData: this.shareData}]);
    }

    goRegister() {
        if (this.shareData == 'null' || !this.shareData) {
            this.wechatService.getWechatId("RegisterPage");
            return
        }
        this.navCtrl.navigateForward(["RegisterPage", {shareData: this.shareData}]);
    }

    qqLogin() {

    }

}
