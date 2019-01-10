import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";

@Injectable()
export class SystemConfigService extends BaseService {
    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "system-config");
    }
}
