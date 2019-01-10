import {Component, ComponentFactoryResolver, Input, OnChanges, SimpleChanges, ViewChild, ViewContainerRef} from '@angular/core';
import {ShowTwoCommoditiesComponent} from "../show-two-commodities/show-two-commodities.component";
import {ShowThreeCommoditiesComponent} from "../show-three-commodities/show-three-commodities.component";
import {ShowFourCommoditiesComponent} from "../show-four-commodities/show-four-commodities.component";
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-show-commodities',
    templateUrl: './show-commodities.component.html',
    styleUrls: ['./show-commodities.component.scss']
})
export class ShowCommoditiesComponent implements OnChanges {
    @Input()
    item;

    @ViewChild('showCommoditiesContent', {read: ViewContainerRef}) _showCommoditiesContent

    _controlRef;

    constructor(public navCtrl: NavController, private componentFactoryResolver: ComponentFactoryResolver) {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.item.currentValue) {
            this._showCommoditiesContent.clear();
            if (changes.item.currentValue.showMode) {
                const controlComponentFactory = this.componentFactoryResolver.resolveComponentFactory(ShowTwoCommoditiesComponent);
                this._controlRef = this._showCommoditiesContent.createComponent(controlComponentFactory);
            }
            this._controlRef.instance.item = this.item;
            this._controlRef.instance.goCommodity = this.goCommodity;
            this._controlRef.instance.goUrl = this.goUrl;
        }
    }

    goCommList() {
        window.location.href = './#/tabs/(home:home)';
        setTimeout(() => {
            this.navCtrl.navigateForward("CommListPage")
        }, 100)
    }

    goCommodity(id, floorId) {
        window.location.href = './#/tabs/(home:home)#' + floorId;
        setTimeout(() => {
            this.navCtrl.navigateForward(["CommodityPage", {id: id}])
        }, 100)
    }

    goUrl(banner) {
        // 1商品2文章
        console.log("banner跳转");
        if (banner.linkType == 1) {
            window.location.href = './#/tabs/(home:home)';
            setTimeout(() => {
                this.navCtrl.navigateForward(["CommodityPage", {id: banner.linkId}])
            }, 100)
        } else if (banner.linkType == 2) {
            window.location.href = './#/tabs/(home:home)';
            setTimeout(() => {
                this.navCtrl.navigateForward(["ArticleDetailPage", {"articleId": banner.linkId}])
            }, 100)
        } else {
            return
        }
    }

}
