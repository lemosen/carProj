import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class SaleOrderService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "saleOrder");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }
    updateOrderState(id, state) {
        return this.getByParams("updateState", { id: id, state: state })
    }





}
