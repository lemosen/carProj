import {Component} from '@angular/core';
import {NavController} from "@ionic/angular";
import {BannerProvider} from "../../services/banner-service/banner";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-recommend',
    templateUrl: './recommend.page.html',
    styleUrls: ['./recommend.page.scss'],
})
export class RecommendPage {

    list;

    constructor(public nativeProvider: NativeProvider, public navCtrl: NavController, public bannerProvider: BannerProvider) {
    }

    ionViewWillEnter() {
        this.bannerProvider.getRecommends().then(e => {
            if (e.result == "SUCCESS") {
                this.list = e.data;
                this.transform(this.list);
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    transform(data) {
        if (data) {
            data.forEach(e => {
                e.commodities = e.commodities.map(e1 => {
                    return {
                        id: e1.id,
                        imgPath: e1.imgPath,
                        productName: e1.commodityName,
                        productShortName: e1.commodityShortName,
                        discountInfo: e1.discountInfo,
                        originalPrice: e1.originalPrice,
                        currentPrice: e1.currentPrice,
                    }
                });
            })
        }
    }

    goCommodity(id) {
        this.navCtrl.navigateForward(["CommodityPage", {id: id}])
    }
}
