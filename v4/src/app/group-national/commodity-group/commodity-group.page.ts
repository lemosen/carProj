import {Component, OnInit, ViewChild} from '@angular/core';
import {AttrGroupModalPage} from "../attr-group-modal/attr-group-modal.page";
import {NativeProvider} from "../../../services/native-service/native";
import {Events, ModalController, NavController} from "@ionic/angular";
import {GroupPurchaseModalPage} from "../group-purchase-modal/group-purchase-modal.page";
import {ActivatedRoute} from "@angular/router";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";
import {DomSanitizer} from "@angular/platform-browser";
import {REFRESH_SHOPPINGCART} from "../../Constants";
import {ServiceModalPage} from "../../commodity-modal/service-modal/service-modal.page";
import {TimeCountComponent} from "../../components/time-count/time-count.component";
import {ShareModalPage} from "../../share-modal/share-modal.page";
import {MemberProvider} from "../../../services/member-service/member";
import {WechatService} from "../../../services/wechat-service/wechat.service";

@Component({
    selector: 'app-commodity-group',
    templateUrl: './commodity-group.page.html',
    styleUrls: ['./commodity-group.page.scss'],
})
export class CommodityGroupPage implements OnInit {
    select: string = "introduction";

    /*拼团信息*/
    groupInfo;

    /*商品信息*/
    commodity;

    preMemberId;
    /*开团成员(前两条*/
    groupMember = [];

    //该货品规格
    attr: string;

    @ViewChild('timeCount') timeCount: TimeCountComponent;

    constructor(public wechatProvider: WechatService,public nativeProvider: NativeProvider, public navCtrl: NavController, public modalCtrl: ModalController, public route: ActivatedRoute,
                public activeProvider: GroupActiveService, public domSanitizer: DomSanitizer, public events: Events,) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.route.data.subscribe((data) => {
            this.groupInfo = data.data;
            this.commodity = this.groupInfo.commodity;
            this.groupMember = this.groupInfo.nationalGroupRecords.slice(0, 3);

            this.groupInfo.product.attributes.forEach(e => this.attr += (e.attrValue + " "));
            this.timeCount.setEndTime(this.groupInfo.endTime);
        });

        //页面跳转的初始化切换，供其他页面进入评价或详情
        if (this.route.params["value"].segment != undefined) {
            this.select = this.route.params["value"].segment;
        }
    }

    //打开已开团列表
    async openPurchaseModal() {
        const modal = await this.modalCtrl.create({
                component: GroupPurchaseModalPage,
                componentProps: this.groupInfo
            }
        );
        await modal.present();
    }

    //开启拼团
    async goGroupWriteOrder() {
        const modal = await this.modalCtrl.create({
            component: AttrGroupModalPage,
            componentProps: {data: this.groupInfo, orderType: 'openGroup'},
        });
        await  modal.present()
    }

    //参与拼团
    async joinGroup(item) {
        const modal = await this.modalCtrl.create({
            component: AttrGroupModalPage,
            componentProps: {data: this.groupInfo, groupId: item.id, orderType: 'joinGroup'}
        });
        await modal.present()
    }

    async paramsModal() {
        // const modal = await this.modalCtrl.create({
        //         component: ParamsModalPage,
        //         componentProps: {attr: this.commodity.attributeGroups}
        //     }
        // );
        // await modal.present();
    }

    async serviceModal() {
        const modal = await this.modalCtrl.create({
                component: ServiceModalPage,
            }
        );
        await modal.present();
    }

    goShoppingCart() {
        this.events.publish(REFRESH_SHOPPINGCART);
        this.navCtrl.navigateForward("/tabs/(shoppingCart:shoppingCart)");
    }

    onCustom($event) {
        this.select = $event;
    }

    async openShareModal() {
        let memberId;
        if (MemberProvider.isLogin()) {
            memberId = MemberProvider.getLoginMember().id
        }
        let shareData = {
            title: this.commodity.commodityName,
            desc: this.commodity.commodityShortName,
            link: encodeURI(window.location.href.split('#')[0] + 'wechatShare.html?memberId=' + memberId + "&type=commodity&contentId=" + this.commodity.id + "?"),
            imgUrl: this.commodity.imgPath,
        };
        this.wechatProvider.share(shareData);
        const modal = await this.modalCtrl.create({
                component: ShareModalPage,
            }
        );
        await modal.present();
    }
}
