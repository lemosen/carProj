import {Component, ViewChild} from '@angular/core';
import {AlertController, Content, NavController} from "@ionic/angular";
import {Banner} from "../../domain/original/banner.model";
import {BannerProvider} from "../../services/banner-service/banner";
import {NativeProvider} from "../../services/native-service/native";
import {SortPipe} from "../../pipes/sort/sort";
import {PageQuery} from "../../util/page-query.model";
import {Commodity} from "../../domain/original/commodity.model";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {MemberProvider} from "../../services/member-service/member";
import {hashChange} from "../../util/window-event";

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss']
})
export class HomePage {

    banners: Banner[] = [];

    commodities = [];

    commodityList: Commodity[] = [];

    pageQuery: PageQuery = new PageQuery();

    @ViewChild('content') content: Content;

    constructor(public alertCtrl: AlertController, public nativeProvider: NativeProvider, public memberProvider: MemberProvider,
                public bannerProviders: BannerProvider, public navCtrl: NavController,
                public commodityProvider: CommodityProvider) {
        //返回锚点事件
        hashChange(this);
        //下滑加载更多商品，一次加载6个商品
        // 最多加载5次，共30个商品
        this.pageQuery.page = 0;
        this.pageQuery.pageSize = 6;
    }

    ionViewWillEnter() {
        this.getData();
    }

    /**
     * 获取页面数据
     * @param refresher
     */
    private getData(refresher?) {
        this.bannerProviders.getHomePageData().then(data => {
            this.formatHomeData(data);
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
        this.memberProvider.autoLogin(window.location.href);
    }

    /**
     * 格式化数据
     * @param data
     */
    private formatHomeData(data) {
        this.banners = data.banner.data;
        this.commodities = data.homeData.data;
        this.commodities.forEach(e => {
            let sortPipe = new SortPipe();
            e.commodities = sortPipe.transform(e.commodities)
        });
    }

    /**
     * 上拉刷新
     */
    doRefresh(data) {
        this.formatHomeData(data)
    }

    /**
     * 下拉加载
     * @param data
     */
    doInfinite(data) {
        this.commodityList = this.commodityList.concat(data.data);
        this.pageQuery = data.pageQuery;
    }

    goCommodity(id, floorId) {
        window.location.href = './#/tabs/(home:home)#' + floorId;
        setTimeout(() => {
            this.navCtrl.navigateForward(["CommodityPage", {id: id}])
        }, 100)
    }
}
