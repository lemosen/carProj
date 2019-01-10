import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class SupplierService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
    super(http, appConfig, "supplier");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

    getList(){
        return this.getByParams("getList", {});
    }
    getPlatformdata(){
        return this.getByParams("getPlatformdata", {});
    }

    getSalesList(id){
        return this.getByParams("salesList", {id});
    }

    /**
     * 账户冻结
     * @param id
     * @param shel=0启用  shelf=1禁用
     * @returns {Observable<any>}
     */
    banKai(supplierId){
        return this.getByParams("updateShelf", {supplierId: supplierId })
    }



}
