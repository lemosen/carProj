import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class CommodityService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "commodity");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    /**
     * 商品上下架
     * @param id
     * @param shelf 下架 shelf=2
     * @returns {Observable<any>}
     */
    upAndDown(commodityId){
        return this.getByParams("upAndDown",{commodityId:commodityId})
    }

}
