import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierSaleStatService} from '../../../services/supplier-sale-stat.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {SupplierSaleStat} from "../../../models/original/supplier-sale-stat.model";

@Component({
  selector: 'list-supplier-sale-stat',
  templateUrl: './list-supplier-sale-stat.component.html',
  styleUrls: ['./list-supplier-sale-stat.component.less'],
  encapsulation: ViewEncapsulation.None
})
export class ListSupplierSaleStatComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  statistics: SupplierSaleStat;

  supplierGroupings: SupplierSaleStat[];

  loading = false;

  datas: any[] = [];
  dateFormat = 'yyyy/MM/dd';
  date: Date[] = [new Date, new Date]
  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private supplierSaleStatService: SupplierSaleStatService, public msg: NzMessageService,
              private fb: FormBuilder) {
    this.buildForm();
  }

  supplierGrouping(){
    this.supplierSaleStatService.getSupplierGrouping(this.startTimes, this.endTimes).subscribe(response => {
      this.supplierGroupings = response.data;
    });
  }

  ngOnInit() {
    this.supplierGrouping();
   /* this.searchData();*/
    this.goplatformSaleStatController();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      range: []
    });
  }

  startTimes: String;

  endTimes: String;

  goplatformSaleStatController() {
    this.supplierSaleStatService.getTotalSaleStats().subscribe(response => {
      this.statistics = response.data;
    });
  }

  changeRange(dates: Date[]) {
    if(dates != null) {
      if(dates.length != 0){
        let split = dates[0].toLocaleDateString().split('/');
        let split1 = dates[1].toLocaleDateString().split('/');
        this.startTimes = split[0] + "-" + split[1] + "-" + split[2]
        this.endTimes = split1[0] + "-" + split1[1] + "-" + split1[2]
      }else{
       this.clear();
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
   /* if (reset) {
      this.pageQuery.resetPage();
    }
    this.getData();*/
    this.supplierGrouping();
    this.goplatformSaleStatController();
  }

  getData() {
    // this.loading = true;
    // this.supplierSaleStatService.query(this.pageQuery).subscribe(response => {
    //   this.datas = response['content'];
    //   this.pageQuery.covertResponses(response);
    //   this.loading = false;
    // }, error => {
    //   this.loading = false;
    //   this.msg.error('请求错误' + error.message);
    // });
  }

  clearSearch() {
    this.searchForm.reset({
      id: null,
      guid: null,
      platformOrderNum: null,
      saleAmount: null,
      platformRatio: null,
      cost: null,
      profit: null,
      statTime: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(platformSaleStatId) {
    this.supplierSaleStatService.removeById(platformSaleStatId).subscribe(response => {
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

}
