import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouteReuseStrategy} from '@angular/router';

import {IonicModule, IonicRouteStrategy} from '@ionic/angular';
import {SplashScreen} from '@ionic-native/splash-screen/ngx';
import {StatusBar} from '@ionic-native/status-bar/ngx';
import {FormBuilder} from "@angular/forms";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MemberProvider} from "../services/member-service/member";
import {AppConfig} from "./app.config";
import {NativeProvider} from "../services/native-service/native";
import {BannerProvider} from "../services/banner-service/banner";
import {HttpClientJsonpModule, HttpClientModule} from "@angular/common/http";
import {CommodityProvider} from "../services/commodity-service/commodity";
import {OrderProvider} from "../services/order-service/order";
import {ArticleProvider} from "../services/article-service/article";
import {AccountProvider} from "../services/account-service/account";

import {AttrModalPageModule} from "./commodity-modal/attr-modal/attr-modal.module";
import {ServiceModalPageModule} from "./commodity-modal/service-modal/service-modal.module";
import {ParamsModalPageModule} from "./commodity-modal/params-modal/params-modal.module";
import {StorageVolumePageModule} from "./coupon-about/storage-volume/storage-volume.module";
import {CouponOrderPageModule} from "./coupon-about/coupon-order/coupon-order.module";
import {FileServiceProvider} from "../services/file-service/file-service";
import {ReturnReasonModalPageModule} from "./after-sales/return-reason-modal/return-reason-modal.module";
import {ConsigneeModalPageModule} from "./consignee-modal/consignee-modal.module";
import {Geolocation} from '@ionic-native/geolocation/ngx';

import {FilePath} from "@ionic-native/file-path/ngx";
import {File} from "@ionic-native/file/ngx";
import {FileTransfer} from "@ionic-native/file-transfer/ngx";
import {Camera} from "@ionic-native/camera/ngx";
import {ImagePicker} from "@ionic-native/image-picker/ngx";
import {FileOpener} from "@ionic-native/file-opener/ngx";
import {WechatService} from "../services/wechat-service/wechat.service";
import {CommodityResolverService} from "./commodity/commodity-resolver.service";
import {ShareModalPageModule} from "./share-modal/share-modal.module";
import {ShareClickModalPageModule} from "./share-modal/share-click-modal/share-click-modal.module";
import {StorageVolumeOrderPageModule} from "./coupon-about/storage-volume-order/storage-volume-order.module";
import {DistrictModalPageModule} from "./district-modal/district-modal.module";
import {CouponModalPageModule} from "./coupon-about/coupon-modal/coupon-modal.module";
import {AfterSaleService} from "../services/order-service/after-sale.service";

@NgModule({
    declarations: [AppComponent],
    entryComponents: [],
    imports: [HttpClientModule,
        HttpClientJsonpModule,
        BrowserModule,
        IonicModule.forRoot({backButtonText: '.', mode: "ios"}),
        AppRoutingModule,
        AttrModalPageModule,
        ServiceModalPageModule,
        ParamsModalPageModule,
        StorageVolumePageModule,
        CouponOrderPageModule,
        ReturnReasonModalPageModule,
        ConsigneeModalPageModule,
        ShareModalPageModule,
        ShareClickModalPageModule,
        StorageVolumeOrderPageModule,
        DistrictModalPageModule,
        CouponModalPageModule,
    ],
    providers: [
        StatusBar,
        SplashScreen,
        FormBuilder,
        {provide: RouteReuseStrategy, useClass: IonicRouteStrategy},
        MemberProvider,
        AppConfig,
        NativeProvider,
        BannerProvider,
        CommodityProvider,
        OrderProvider,
        ArticleProvider,
        AccountProvider,
        FileServiceProvider,
        WechatService,
        CommodityResolverService,
        AfterSaleService,
        Geolocation,
        FilePath, File, FileTransfer, Camera, FileOpener, ImagePicker
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
