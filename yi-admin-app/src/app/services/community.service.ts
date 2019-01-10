import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class CommunityService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
    super(http, appConfig, "community");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }
    getList(){
        return this.getByParams("commissionSum", {});
    }

    /**
     * 启用 禁用
     * @param id
     * @param shel=0启用  shelf=1禁用
     * @returns {Observable<any>}
     */
    banKai(communityId){
        return this.getByParams("updateShelf", {communityId: communityId })
    }


}
