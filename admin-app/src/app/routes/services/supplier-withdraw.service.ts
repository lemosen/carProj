import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class SupplierWithdrawService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: HttpClient) {
    super(http,"supplierWithdraw");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }


  /*
  * 发放
  * */
  grant(id){
    return this.getByParams("grant", {id:id});
  }

  /*
  * 驳回
  * */
  reject(id){
    return this.getByParams("reject", {id:id});
  }



    }
