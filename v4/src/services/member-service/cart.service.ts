import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";
import {HttpServiceProvider} from "../http-service/http-service";

@Injectable({
  providedIn: 'root'
})
export class CartService extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "cart");
    }

    addShopCart(memberId, productId, num) {
        const params = new HttpParams().set('memberId', memberId).set('productId', productId).set('num', num);
        return this.get('addShopCart', params);
    }

    deleteCommodity(shoppingCartProductIds) {
        const params = new HttpParams().set('shoppingCartProductId', shoppingCartProductIds);
        return this.get('removeShopCart', params);
    }

    changQuantity(productId, quantity) {
        const params = new HttpParams().set('shoppingCartProductId', productId).set('num', quantity);
        return this.get('changeShopCartNum', params);
    }

    getShoppingCart(memberId) {
        const params = new HttpParams().set('memberId', memberId);
        return this.get("getShopCarts", params);
    }
}
