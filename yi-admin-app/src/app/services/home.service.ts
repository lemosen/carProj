import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";

@Injectable()
export class HomeService extends BaseService {

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "home");
    }

    /**
     * 获取当前员工的首页数据
     * @returns {Observable<any>}
     */
    getHomeData() {
        return this.getByParams("getHomeData", null);
    }


}
