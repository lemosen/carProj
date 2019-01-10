import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class SupplierAccountService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: HttpClient) {
    super(http, "supplierAccount");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  getBySupplier() {
   return  this.getByParams("getBySupplier");
  }


}
