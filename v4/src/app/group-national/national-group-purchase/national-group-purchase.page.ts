import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {PageQuery} from "../../../util/page-query.model";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";
import {NativeProvider} from "../../../services/native-service/native";
import {MemberProvider} from "../../../services/member-service/member";

@Component({
    selector: 'app-national-group-purchase',
    templateUrl: './national-group-purchase.page.html',
    styleUrls: ['./national-group-purchase.page.scss'],
})
export class NationalGroupPurchasePage {

    state = "already";

    pageQuery: PageQuery = new PageQuery();

    /**
     * 获取的数据
     * @type {any[]}
     */
    list = [];

    /**
     * 显示的数据
     * @type {any[]}
     */
    list1 = [];

    isLoading: boolean = false;

    constructor(public groupActiveProvider: GroupActiveService, public navCtrl: NavController, public nativeProvider: NativeProvider) {
    }

    ionViewWillEnter() {
        this.listFilter(this.state)
    }

    listFilter(type) {
        this.queryFilter(type);
    }

    setList() {
        this.list = this.list1;
    }

    queryFilter(type) {
        this.list = [];
        this.isLoading = true;
        switch (type) {
            case "yesterday":
                /*this.groupActiveProvider.getYesterdayPurchase().then(e => {
                    if (e.result == "SUCCESS") {
                        this.list1 = e.data;
                        this.setList();
                        this.isLoading = false;
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                        this.isLoading = false;
                    }
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                    this.isLoading = false;
                });
                break;*/
            case "already":
                this.groupActiveProvider.queryOrder(this.pageQuery.requests).then(e => {
                    this.list1 = e.content;
                    this.setList();
                    this.pageQuery.covertResponses(e);
                    this.isLoading = false;
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                    this.isLoading = false;
                });
                break;
            case "mine":
                /*this.groupActiveProvider.getMyCollage(MemberProvider.getLoginMember().id).then(e => {
                    if (e.result == "SUCCESS") {
                        this.list1 = e.data.map(e => {
                            return e.nationalGroup
                        });
                        this.setList();
                        this.isLoading = false;
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                        this.isLoading = false;
                    }
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                    this.isLoading = false;
                });
                break;*/
        }
    }

    goCommodity(commodity) {
        this.navCtrl.navigateForward(["CommodityGroupPage", {id: commodity.id}])
    }


    filterItems(ev: any){
        let val = ev.target.value;
        this.setList();
        if (val && val.trim() !== '') {
            this.list = this.list.filter(function(item) {
                return (item.product.productName.toLowerCase().includes(val.toLowerCase()) || item.label.toLowerCase().includes(val.toLowerCase()));
            });
        }
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast() && this.state == "already") {
            this.pageQuery.plusPage();
            this.groupActiveProvider.queryOrder(this.pageQuery.requests).then(data => {
                this.list = this.list.concat(data.content);
                this.pageQuery.covertResponses(data);
                infiniteScroll.target.complete();
            }, err => infiniteScroll.target.complete());
        } else {
            infiniteScroll.target.complete();
        }
    }

}
