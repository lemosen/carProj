import {Component} from '@angular/core';
import {ModalController, NavController} from "@ionic/angular";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {Commodity} from "../../domain/original/commodity.model";
import {PageQuery} from "../../util/page-query.model";
import {NativeProvider} from "../../services/native-service/native";
import {ActivatedRoute} from "@angular/router";
import {ScreenPage} from "../screen/screen.page";

const ARROW_ROUND_UP='arrow-round-up';
const ARROW_ROUND_DOWN='arrow-round-down';

@Component({
    selector: 'app-comm-list',
    templateUrl: './comm-list.page.html',
    styleUrls: ['./comm-list.page.scss'],
})
export class CommListPage {
    pageQuery: PageQuery = new PageQuery();
    list: Commodity[] = [];
    sort = 'hot';
    delaySearching = 0;

    isLoading:boolean = false;

    hotSegmentIcon='';
    priceSegmentIcon='';

    constructor(public router: ActivatedRoute, public nativeProvider: NativeProvider, public modalCtrl: ModalController, public navCtrl: NavController, public commodityProvider: CommodityProvider) {
    }

    ionViewWillEnter() {
        this.pageQuery.pushParams("operateCategories.id", this.router.params["value"].id);
        this.commoditiesFilter('hot');
    }

    getItems(event) {
        this.delaySearching += 1;
        setTimeout(() => {
            this.delaySearching -= 1;
            if(this.delaySearching == 0){
                //执行后台请求的代码
                this.pageQuery.pushParams("name", event.target.value);
                this.getData();
            }
        }, 1000)
    }

    commoditiesFilter(type) {
        if (type == 'hot') {
            this.hotSegmentIcon = (this.hotSegmentIcon == ARROW_ROUND_DOWN ? ARROW_ROUND_UP : ARROW_ROUND_DOWN);
            this.priceSegmentIcon = '';
            this.pageQuery.onlySort("saleQuantity",'desc');
            this.pageQuery.requests.resetPage();
            this.getData();
        }
        if (type == 'price') {
            this.priceSegmentIcon = (this.priceSegmentIcon == ARROW_ROUND_DOWN ? ARROW_ROUND_UP : ARROW_ROUND_DOWN);
            this.hotSegmentIcon = '';
            this.pageQuery.onlySort("currentPrice", 'desc');
            this.pageQuery.requests.resetPage();
            this.getData();
        }
    }

    private getData() {
        this.isLoading = true;
        this.commodityProvider.query(this.pageQuery.requests).then(data => {
            this.isLoading = false;
            this.transform(data);
            this.list = data.content;
        }, err => {
            this.isLoading = false;
            this.nativeProvider.showToastFormI4(err.message);
        })
    }

    async goScreen() {
        const modal1 = await this.modalCtrl.create({
            component: ScreenPage,
        });
        await modal1.present();

        await modal1.onDidDismiss().then(data => {
            //会添加两个价格 所以要删除两次
            this.pageQuery.removeByNameFilter('currentPrice');
            this.pageQuery.removeByNameFilter('currentPrice');
            if (data.data[0] != null) {
                this.pageQuery.addFilter("currentPrice", data.data[0], 'lte');
                this.pageQuery.addFilter("currentPrice", data.data[1], 'gte')
            }
            this.getData();
        });
    }

    goCommodity(commodity) {
        this.navCtrl.navigateForward(["CommodityPage", {id: commodity.id}]);
    }

    doRefresh(refresher) {
        this.pageQuery.resetRequests();
        this.getData();
        this.commodityProvider.query(this.pageQuery.requests).then(data => {
                this.transform(data);
                this.pageQuery.covertResponses(data);
                refresher.target.complete();
            },
            err => refresher.target.complete());
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.commodityProvider.query(this.pageQuery.requests).then(data => {
                    this.transform(data);
                    this.list = this.list.concat(data.content);
                    this.pageQuery.covertResponses(data);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

    transform(data) {
        data.content = data.content.map(e => {
            return {
                id: e.id,
                imgPath: e.imgPath,
                productName: e.commodityName,
                productShortName: e.commodityShortName,
                discountInfo: e.discountInfo,
                originalPrice: e.originalPrice,
                currentPrice: e.currentPrice,
            }
        });
    }

}
