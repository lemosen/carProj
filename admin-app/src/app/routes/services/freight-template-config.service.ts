import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {BaseService} from "@core/startup/base.service";



@Injectable()
export class FreightTemplateConfigService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: HttpClient) {
    super(http, "freightTemplateConfig");
  }

  setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
  }

}
