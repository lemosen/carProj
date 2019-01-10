import {Component, OnInit} from '@angular/core';

import {ModalController, NavController} from "@ionic/angular";
import {Member} from "../../domain/original/member.model";
import {MemberProvider} from "../../services/member-service/member";
import {NativeProvider} from "../../services/native-service/native";
import {BannerProvider} from "../../services/banner-service/banner";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {AppConfig} from "../app.config";
import {ShareClickModalPage} from "../share-modal/share-click-modal/share-click-modal.page";
import {SUCCESS} from "../Constants";
import {PageQuery} from "../../util/page-query.model";

@Component({
    selector: 'app-invites-courtesy',
    templateUrl: './invites-courtesy.page.html',
    styleUrls: ['./invites-courtesy.page.scss'],
})
export class InvitesCourtesyPage implements OnInit {
    list: Member[];
    member: Member;

    advertisingCommodity;

    integral;

    pageQuery: PageQuery = new PageQuery();

    /**
     * 邀请人数
     */
    invitedTotal;

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController, public bannerProvider: BannerProvider,
                public wechatProvider: WechatService, public modalCtrl: ModalController, public appConfig: AppConfig) {
        this.member = MemberProvider.getLoginMember();
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.memberProvider.getIntegralRule(2).then(e => {
            if (e.result == "SUCCESS") {
                this.integral = e.data;
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message)
        });
        this.getMemberInfo();
        this.getMemberLv1();
        this.getAdvertisingCommodityData();
        this.initShareData();
    }

    async openShareModal() {
        const modal = await this.modalCtrl.create({
                component: ShareClickModalPage,
            }
        );
        await modal.present();
    }

    getMemberInfo() {
        this.memberProvider.getMember(this.member.id).then(e => {
            if (e.result == SUCCESS) {
                this.member = e.data;
            }
        })
    }

    getMemberLv1() {
        this.pageQuery.resetRequests();
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.pageQuery.addSort('createTime', 'desc');
        this.memberProvider.getMemberLv1(this.pageQuery.requests).then(e => {
            this.pageQuery.covertResponses(e);
            this.list = e.content;
            this.invitedTotal=this.pageQuery.responses.totalElements;
        });
    }

    /*广告*/
    getAdvertisingCommodityData() {
        this.bannerProvider.getAdvertisingCommodity(5).then(data => {
            if (data.result == "SUCCESS") {
                this.advertisingCommodity = data.data;
            } else {
                this.nativeProvider.showToastFormI4(data.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    initShareData() {
        let shareData = {
            title: '邀请有礼',
            desc: '加入我们的团队,登峰造极',
            link: encodeURI(window.location.href.split('#')[0] + 'wechatShare.html?RegisterPage&preMemberId=' + this.member.id + '?'),
            imgUrl: window.location.href.split('#')[0] + 'assets/imgs/服务中心logo.png',
        };
        this.wechatProvider.share(shareData);
    }

    goRewardRule() {
        this.navCtrl.navigateForward("RewardRulePage")
    }

    goInviteRule() {
        this.navCtrl.navigateForward("InviteRulePage")
    }

    goMyTeam() {
        this.navCtrl.navigateForward("MyTeamPage")
    }

    goCommissionOrder() {
        this.navCtrl.navigateForward("CommissionOrderPage")
    }


}
