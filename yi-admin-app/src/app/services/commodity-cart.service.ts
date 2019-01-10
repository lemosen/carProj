import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class CommodityCartService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "commodityCart");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }


}
