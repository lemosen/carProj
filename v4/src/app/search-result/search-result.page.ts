import {Component, ViewChild} from '@angular/core';
import {Commodity} from "../../domain/original/commodity.model";
import {PageQuery} from "../../util/page-query.model";
import {NavController, Searchbar} from "@ionic/angular";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {NativeProvider} from "../../services/native-service/native";
import {Storage} from "@capacitor/core";
import {MemberProvider} from "../../services/member-service/member";

const HISTORY_SEARCH = 'HISTORY_SEARCH';

@Component({
    selector: 'app-search-result',
    templateUrl: './search-result.page.html',
    styleUrls: ['./search-result.page.scss'],
})
export class SearchResultPage {

    list: Commodity[] = [];

    pageQuery: PageQuery = new PageQuery();

    delaySearching = 0;

    historySearch: string[] = [];

    searchItem: string;

    tips: string;

    isSearching:boolean = false;

    @ViewChild('searchbar') searchbar:Searchbar;
    constructor(public nativeProvider: NativeProvider, public commodityProvider: CommodityProvider, public navCtrl: NavController, public memberProvider:MemberProvider) {
        this.loadHistory();
    }

    ionViewWillEnter() {
        this.searchbar.focus();
    }

    getItems(event) {
        this.searchResult(event.target.value);
    }

    private searchResult(value) {
        this.tips = '';
        if (value == "") {
            this.list = [];
            return
        }

        this.delaySearching += 1;
        setTimeout(() => {
            this.delaySearching -= 1;
            if (this.delaySearching == 0) {
                //执行后台请求的代码
                this.pageQuery.pushParams("name", value);
                this.getData(this.pageQuery);

                this.saveHistory(value)
            }
        }, 1000);
    }

    private getData(page) {
        this.isSearching = true;
        this.pageQuery.pushParams("city", this.memberProvider.getGpsLocation());
        this.commodityProvider.query(page.requests).then(data => {
            this.transform(data);
            this.list = data.content;
            this.tips = "无相关商品~";
            this.isSearching = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isSearching = false;
        })
    }

    async homeBack() {
        this.navCtrl.goBack(false)
    }

    detail(commodity) {
        this.navCtrl.navigateForward(["CommodityPage", {id: commodity.id}]);
    }

    setKey(value) {
        this.searchItem = value;
        this.searchResult(value);
    }

    /**
     * 搜索历史 缓存部分
     */
    saveHistory(value) {
        if(value == ""){
            return
        }
        if (this.historySearch.some((e => {return e == value}))) {
            return
        }
        if (this.historySearch.length >= 5) {
            this.historySearch.pop();
        }
        this.historySearch.unshift(value);
        Storage.set({key: HISTORY_SEARCH, value: JSON.stringify(this.historySearch)});
    }

    async loadHistory() {
        let historyItem = await Storage.get({key: HISTORY_SEARCH});
        if(!historyItem.value){
            this.historySearch = [];
        }else {
            this.historySearch = JSON.parse(historyItem.value);
        }
    }

    removeHistory() {
        this.historySearch = [];
        Storage.remove({key: HISTORY_SEARCH});
    }

    doRefresh(refresher) {
        this.pageQuery.resetRequests();
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
                productName: e.commodityName,
                imgPath: e.imgPath,
                productShortName: e.commodityShortName,
                discountInfo: e.discountInfo,
                originalPrice: e.originalPrice,
                currentPrice: e.currentPrice,
            }
        });
    }


}
