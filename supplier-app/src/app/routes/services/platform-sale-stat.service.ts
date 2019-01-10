import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class PlatformSaleStatService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: HttpClient) {
    super(http, "platformSaleStat");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

    search(startTime,endTime){
      return this.getByParams("saleOrderItem", {"startTime":startTime,"endTime":endTime});

    }


    }
