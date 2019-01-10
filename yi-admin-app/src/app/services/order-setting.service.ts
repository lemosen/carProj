import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class OrderSettingService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
    super(http, appConfig, "orderSetting");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

    updateMinuteValue(id,minute){
        return this.getByParams("updateMinuteValue",{id:id,minute:minute})
    }

    updateDayValue(id,day){
        return this.getByParams("updateDayValue",{id:id,day:day})
    }

    }
