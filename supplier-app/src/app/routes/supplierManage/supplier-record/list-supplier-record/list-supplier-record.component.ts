import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierWithdrawService} from '../../../services/supplier-withdraw.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-supplier-withdraw',
  templateUrl: './list-supplier-record.component.html',
  styleUrls: ['./list-supplier-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListSupplierRecordComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private supplierWithdrawService: SupplierWithdrawService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    /* this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
      this.searchData();*/
    this.pageQuery.addOnlyFilter('state', 3, 'eq');
    this.getData();
  }

  buildForm() {
    this.searchForm = this.fb.group({

      supplierName: [null],

      payee: [null],

    });
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value)
    this.searchData();
  }

  searchData(reset: boolean = false): void {
    this.pageQuery.addOnlyFilter('state', 3, 'eq');
    if (reset) {
      this.pageQuery.resetPage();
    }
    this.configPageQuery(this.pageQuery);
    this.getData();
  }

  getData() {
    this.loading = true;
    this.supplierWithdrawService.query(this.pageQuery).subscribe(response => {
      this.datas = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      supplierName: null,
      payee: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(supplierWithdrawId) {
    this.supplierWithdrawService.removeById(supplierWithdrawId).subscribe(response => {
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
    if (searchObj.supplierName != null) {
      pageQuery.addOnlyFilter('supplierName', searchObj.supplierName, 'contains');
    }
    if (searchObj.payee != null) {
      pageQuery.addOnlyFilter('payee', searchObj.payee, 'contains');
    }
    return pageQuery;
  }


}
