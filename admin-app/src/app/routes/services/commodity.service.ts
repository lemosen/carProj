import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from 'rxjs/internal/BehaviorSubject';


@Injectable()
export class CommodityService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: HttpClient) {
    super(http, "commodity");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  /**
   * 报考上下架
   * @param id
   * @param shelf 下架 shelf=2
   * @returns {Observable<any>}
   */
  upAndDown(commodityId) {
    return this.getByParams("upAndDown", {commodityId: commodityId})
  }

  /**
   *   禁用
   */
  disable(id) {
    return this.getByParams("disable", {id: id})
  }

  /**
   * 启用
   */
  enable(id) {
    return this.getByParams("enable", {id: id})
  }


  getAttributeGroupsByCategoryId(categoryId: number) {
    return this.getByParams("getAttributeGroupsByCategoryId", {categoryId: categoryId})
  }

  /**
   * 上架
   * @param {any[]} ids
   * @returns {Observable<any>}
   */
  batchUpperShelf(ids: number[]) {
    return this.postData("batchUpperShelf", ids)
  }

  /**
   * 下架
   * @param {any[]} ids
   * @returns {Observable<any>}
   */
  batchLowerShelf(ids: number[]) {
    return this.postData("batchLowerShelf", ids)
  }
}
