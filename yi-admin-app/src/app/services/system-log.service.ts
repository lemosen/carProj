import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class SystemLogService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "systemLog");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }


}
