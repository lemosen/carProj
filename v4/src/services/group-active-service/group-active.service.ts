import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
  providedIn: 'root'
})
export class GroupActiveService  extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "groupBuyActivity");
    }

    queryOrder(type) {
        return this.post("query", type);
    }

    getGroupActive(commodityId) {
        // const params = new HttpParams().set('commodityId', commodityId);
        // return this.get("goGroupPurchase", params);
    }

    getMyCollage(memberId) {
        // const params = new HttpParams().set('memberId', memberId);
        // return this.get("myCollage", params);
    }

    getYesterdayPurchase() {
        // return this.get("yesterdayPurchase");
    }

    getGroupProduct(groupId) {
        // const params = new HttpParams().set('nationalGroupId', groupId);
        // return this.get("collageGoods", params);
    }



}
