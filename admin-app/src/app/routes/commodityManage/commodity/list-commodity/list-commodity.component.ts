import {Component, OnInit} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CommodityService} from '../../../services/commodity.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'list-commodity',
  templateUrl: './list-commodity.component.html',
})
export class ListCommodityComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  datas: any[] = [];

  loading = false;

  expandForm = false;

  listCommodityId = [];
  select = [];
  button = false;

  constructor(public route: ActivatedRoute, private router: Router, private commodityService: CommodityService, public msg: NzMessageService, private fb: FormBuilder, public modalService: NzModalService) {
    this.buildForm();
  }

  menus = [
    {name: "全部报考", value: ""},
  ];

  onItemClick(i) {
    this.pageQuery.clearFilter();
    this.pageQuery.addOnlyFilter('shelf', this.menus[i].value, 'eq');
    this.pageQuery.addLockFilterName('shelf');
    this.getData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      commodityName: [null],
      supplierName: [null]
    });
  }

  ngOnInit() {
    this.pageQuery.addOnlyFilter('state', 2, 'eq');
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
      supplierName: null,
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

  onTheShelf(commodityId) {
    this.commodityService.upAndDown(commodityId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("上架成功");
        /*this.router.navigate(['/pages/member/list']);*/
        this.allElection = false;
        this.button = false;
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }


  lowerFrame(commodityId) {
    this.commodityService.upAndDown(commodityId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("下架成功");
        /*this.router.navigate(['/pages/member/list']);*/
        this.allElection = false;
        this.button = false;
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
    if (searchObj.supplierName != null) {
      pageQuery.addOnlyFilter('supplier.supplierName', searchObj.supplierName, 'contains');
    }
    return pageQuery;
  }

  /**
   * 全选
   * @param {boolean} value
   */
  checkAll(value: boolean): void {
    this.button = value;
    this.datas.forEach(e => {
      e.checked = value;
    })

  }

  allElection = false;

  /**
   * 单个选择
   */
  refreshStatus(item, id): void {
    this.datas.forEach(e => {
      if (id == e.id && item.checked == true) {
        e.c = true;

      } else if (id == e.id && item.checked == false) {
        e.checked = false;
      }
      this.allElection = true;
      this.button = false;
      this.datas.forEach(e => {
        if (!e.checked) {
          this.allElection = false;
          return
        }
        if (e.checked) {
          this.button = true;
        }
      })
    })
  }

  /**
   * 批量上架
   */
  batchShelves() {
    this.listCommodityId = [];
    this.datas.forEach(e => {
      if (e.checked && e.shelf == 2) {
        this.listCommodityId.push(e.id)
      }
    })
    this.commodityService.batchUpperShelf(this.listCommodityId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("批量上架成功");
        this.allElection = false;
        this.button = false;
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }

  batchDownShelf() {
    this.listCommodityId = [];
    this.datas.forEach(e => {
      if (e.checked && e.shelf == 1) {
        this.listCommodityId.push(e.id)
      }
    })
    this.commodityService.batchLowerShelf(this.listCommodityId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("批量下架成功");
        this.allElection = false;
        this.button = false;
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }

}
