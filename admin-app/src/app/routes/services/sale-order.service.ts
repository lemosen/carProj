import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {BaseService} from "@core/startup/base.service";


@Injectable()
export class SaleOrderService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
        super(http, "saleOrder");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }
    updateOrderState(id, state) {
        return this.getByParams("updateState", { id: id, state: state })
    }

    updateProvince(params: any) {
        return this.postData("updateProvince", params)
    }

    updateDiscount(params: any) {
        return this.postData("updatePrice", params)
    }
    goDelivery(id, trackingNo, logisticsCompany) {
        return this.getByParams("delivery", { id: id, trackingNo: trackingNo, logisticsCompany: logisticsCompany })
    }

    goHomeData() {
        return this.getByParams("homeData")
    }


}
