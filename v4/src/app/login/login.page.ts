import {Component} from '@angular/core';
import {Events, NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {ShareData} from "../../domain/original/share-data.model";
import {ActivatedRoute} from "@angular/router";
import {inputHandle} from "../../util/bug-util";
import {NativeProvider} from "../../services/native-service/native";
import {REFRESH_CUSTOMERCENTER, SUCCESS} from "../Constants";
import {SmsService} from "../../services/common-service/sms.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.page.html',
    styleUrls: ['./login.page.scss'],
})
export class LoginPage {
    /**
     * 分享参数(包含分享人id和用户wechat参数
     */
    shareData;
    heightMark=0;
    hrt=0;
    params = {
        usertel: '',
        vcode: '',
    };

    //验证码参数
    verifyCode: any = {
        verifyCodeTips: "发送验证码",
        countdown: 59,
        disable: true
    };

    isAgree = true;

    navCtrl1:any;

    constructor(public memberProvider: MemberProvider, public navCtrl: NavController, public route: ActivatedRoute, public nativeProvider: NativeProvider,
                public events: Events,public smsProvider: SmsService,) {
        this.navCtrl1 = this.navCtrl;
    }

    ngOnInit() {
        let href = window.location.href;

        if (href.indexOf('shareData') != -1){
            this.shareData = JSON.parse(this.route.params["value"].shareData);
        } else if(href.indexOf('shareData') == -1 && href.indexOf('preMemberId') != -1){
            this.shareData.preMemberId = href.split('preMemberId=')[1].split('?')[0]
        }

        inputHandle()
    }

    goBack() {
        if (this.navCtrl1.stack.length > 1) {
            this.navCtrl.goBack(false)
        } else{
            this.navCtrl.navigateRoot('/tabs/(home:home)')
        }
    }

    login() {
        if(!this.params.usertel || !this.params.vcode){
            this.nativeProvider.showToastFormI4("请输入完整信息");
            return;
        }

        if(!this.isAgree){
            this.nativeProvider.showToastFormI4("请仔细阅读并同意《用户服务协议》");
            return;
        }

        let memberBo = {
            phone: this.params.usertel,
            smsCode: this.params.vcode,
            avater: '',
            nickname: '',
            openId: '',
            unionId: '',
            parentId:''
        };

        if (window.localStorage) {
            if (window.localStorage.getItem('wechatData')) {
                let wechatData = JSON.parse(window.localStorage.getItem('wechatData'));
                memberBo.avater = wechatData.data.headimgurl;
                memberBo.nickname = wechatData.data.nickname;
                memberBo.openId = wechatData.data.openid;
                memberBo.unionId = wechatData.data.unionid;
            }
        }

        if(this.shareData){
            memberBo.parentId = this.shareData.preMemberId;
        }

        let href = window.location.href;
        if(href.indexOf('shareData') == -1 && href.indexOf('preMemberId') != -1){
            memberBo.parentId = href.split('preMemberId=')[1].split('?')[0]
        }

        this.memberProvider.loginBySms(memberBo).then(e => {
            if (e.result == SUCCESS) {
                this.memberProvider.setLoginMember(e.data);
                this.events.publish(REFRESH_CUSTOMERCENTER);
                this.goBack();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    settime() {
        if (this.verifyCode.countdown == 1) {
            this.verifyCode.countdown = 59;
            this.verifyCode.verifyCodeTips = "获取验证码";
            this.verifyCode.disable = true;
            return;
        } else {
            this.verifyCode.countdown--;
        }

        this.verifyCode.verifyCodeTips = this.verifyCode.countdown + "s";
        setTimeout(() => {
            this.verifyCode.verifyCodeTips = this.verifyCode.countdown + "s";
            this.settime();
        }, 1000);
    }

    getCode() {
        if(!this.verifyCode.disable){
            return
        }
        if (this.params.usertel.toString().length !== 11) {
            this.nativeProvider.showToastFormI4("请输入正确的手机号码");
            return;
        }
        this.smsProvider.sendLoginCode(this.params.usertel.toString()).then(e => {
            if (e.result == "SUCCESS") {
                //发送验证码成功后开始倒计时
                this.verifyCode.disable = false;
                this.settime();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    goAgreement() {
        this.navCtrl.navigateForward("AgreementPage")
    }

    /*

    href = window.location.href;

    shareData: string;


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

    }*/

}
