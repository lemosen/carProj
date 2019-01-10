import {Injectable} from '@angular/core';
import {BaseService} from "../../../services/base.service";
import {HttpClient} from "@angular/common/http";
import {AppConfig} from "../../configs/app-config";

@Injectable()
export class ObjectLogService extends BaseService {

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "objectLogs");
    }

}

