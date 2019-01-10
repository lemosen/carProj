import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {AppConfig} from "../pages/configs/app-config";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {RescService} from "./resc.service";


@Injectable()
export class CategoryService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "category");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }
    rescService: RescService
    getAllCategory() {
       return this.getByParams("getAll")
    }

    updateCategory(category:any){
        return this.postData("updateCategory",{category})
    }


}
