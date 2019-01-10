import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {PlatformSaleStatService} from '../../../services/platform-sale-stat.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {PlatformSaleStat} from "../../../models/original/platform-sale-stat.model";
import {SupplierAccountService} from "../../../services/supplier-account.service";
import {SupplierAccount} from "../../../models/original/supplier-account.model";
import {SaleOrderService} from "../../../services/sale-order.service";

@Component({
  selector: 'list-my-income',
  templateUrl: './list-my-income.component.html',
  styleUrls: ['./list-my-income.component.less'],
  encapsulation: ViewEncapsulation.None
})
export class ListMyIncomeComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  statistics: PlatformSaleStat;

  loading = false;

  datas: any[] = [];

  saleOrders: any[] = [];

  supplierAccount: SupplierAccount = new SupplierAccount();

  dateFormat = 'yyyy/MM/dd';
  date: Date[] = [new Date, new Date]
  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, public  supplierAccountService: SupplierAccountService, private platformSaleStatService: PlatformSaleStatService, public msg: NzMessageService, public saleOrderService: SaleOrderService,
              private fb: FormBuilder) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      createTime: [null]
    });
  }

  startTimes: String;

  endTimes: String;

  goplatformSaleStatController() {
    this.platformSaleStatService.search(this.startTimes, this.endTimes).subscribe(response => {
      this.statistics = response.data;
    });
  }

  changeRange(dates: Date[]) {
    if (dates != null) {
      if (dates.length != 0) {
        let split = dates[0].toLocaleDateString().split('/');
        let split1 = dates[1].toLocaleDateString().split('/');
        this.startTimes = split[0] + "-" + split[1] + "-" + split[2]
        this.endTimes = split1[0] + "-" + split1[1] + "-" + split1[2]
      } else {
        this.searchForm.patchValue({
          createTime: null
        })
        this.searchData();
      }
    }
  }

  clear() {
    this.startTimes = "",
      this.endTimes = ""
    this.goplatformSaleStatController();
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value)
    this.searchData();
  }

  searchData(reset: boolean = false): void {
    if (reset) {
      this.pageQuery.resetPage();
    }
    this.configPageQuery(this.pageQuery);
    this.getData();
  }

  getData() {
    this.loading = true;
    this.supplierAccountService.getBySupplier().subscribe(response => {
      this.supplierAccount = response.data;
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
    this.saleOrderService.query(this.pageQuery).subscribe(response => {
      this.saleOrders = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    })
  }

  clearSearch() {
    this.searchForm.reset({
      createTime: null
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(platformSaleStatId) {
    this.platformSaleStatService.removeById(platformSaleStatId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("删除成功");
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }

  private configPageQuery(pageQuery: PageQuery) {
    pageQuery.clearFilter();
    const searchObj = this.searchForm.value;
    if (searchObj.createTime != null) {
      pageQuery.addFilter('createTime', this.startTimes, 'gte');
      pageQuery.addFilter('createTime', this.endTimes, 'lte');
    }
    return pageQuery;
  }

}
