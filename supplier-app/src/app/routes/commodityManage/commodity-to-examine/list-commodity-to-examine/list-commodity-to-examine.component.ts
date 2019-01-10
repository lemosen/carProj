import {Component, OnInit} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CommodityService} from '../../../services/commodity.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-commodity-to-examine',
  templateUrl: './list-commodity-to-examine.component.html',
})
export class ListCommodityToExamineComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  datas: any[] = [];

  loading = false;

  expandForm = false;

  constructor(public route: ActivatedRoute, private router: Router, private commodityService: CommodityService, public msg: NzMessageService, private fb: FormBuilder, public modalService: NzModalService) {
    this.buildForm();
  }

  menus = [
    {name: "待审核", value: 1},
    {name: "已拒绝", value: 3},
  ];

  onItemClick(i) {
    this.pageQuery.clearFilter();
    this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
    this.pageQuery.addLockFilterName('state');
    this.getData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      commodityName: [null],
    });
  }

  ngOnInit() {
    this.pageQuery.addOnlyFilter('state', 1, 'eq');
    this.pageQuery.addLockFilterName('state');
    this.searchData();
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
    this.commodityService.query(this.pageQuery).subscribe(response => {
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
      commodityName: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(commodityId) {
    this.commodityService.removeById(commodityId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("删除成功");
        // this.pageQuery.requests.page = 1;
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }

  applyOnTheShelf(commodityId) {
    this.commodityService.applyOnTheShelf(commodityId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("已重新申请审批，请等待。");
        /*this.router.navigate(['/pages/member/list']);*/
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
    if (searchObj.commodityName != null) {
      pageQuery.addOnlyFilter('commodityName', searchObj.commodityName, 'contains');
    }
    return pageQuery;
  }

}
