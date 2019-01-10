import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class MemberService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
    super(http, appConfig, "member");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

    memberLevel(){
        return this.getByParams("memberLevel", {});
    }

    prohibition(memberId){
      return this.getByParams("prohibition",{memberId:memberId})
    }


    }
