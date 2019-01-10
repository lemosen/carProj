import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class RoleService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
    super(http, appConfig, "role");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

    jurisdiction(role: any){
        return this.postData("jurisdiction",role);
    }





    }
