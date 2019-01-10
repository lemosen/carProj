import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";


@Injectable()
export class AfterSaleOrderService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: HttpClient) {
    super(http, "afterSaleOrder");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

}
