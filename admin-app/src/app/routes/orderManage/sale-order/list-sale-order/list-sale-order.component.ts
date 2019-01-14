import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SaleOrderService} from '../../../services/sale-order.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {ExpressService} from "../../../services/express.service";
import {SaleOrderListVo} from "../../../models/domain/listVo/sale-order-list-vo.model";

@Component({
  selector: 'list-sale-order',
  templateUrl: './list-sale-order.component.html',
  styleUrls: ['./list-sale-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListSaleOrderComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];
  //saleOrderList : Array<SaleOrderListVo>

  expandForm = false;

  dateFormat = 'yyyy/MM/dd';
  startTimes: String;
  endTimes: String;

  constructor(public route: ActivatedRoute, public router: Router, private saleOrderService: SaleOrderService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService, private expressService: ExpressService) {//Express
    this.buildForm();
  }

  menus = [
    {name: "全部订单", value: ""},
    {name: "待付款", value: 1},
    {name: "待预约", value: 2},
    {name: "已审核", value: 3},
    {name: "已完成", value: 4},
    {name: "已关闭", value: 5},
  ];

  onItemClick(i) {
    this.pageQuery.clearFilter();
    this.pageQuery.addOnlyFilter('orderState', this.menus[i].value, 'eq');
    this.pageQuery.addLockFilterName('orderState');
    this.getData();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      username: [null],
      orderNo: [null],
      createTime: [null],
      consignee: [null],
      consigneePhone: [null],
    });
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
    this.saleOrderService.query(this.pageQuery).subscribe(response => {
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
      username: null,
      orderNo: null,
      createTime: null,
      consignee: null,
      consigneePhone: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(saleOrderId) {
    this.saleOrderService.removeById(saleOrderId).subscribe(response => {
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

  goUpdateOrderState(id, orderState) {
    this.saleOrderService.updateOrderState(id, orderState).subscribe(response => {
      if (response.result == SUCCESS) {
        this.getData();
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    })

  }

  private configPageQuery(pageQuery: PageQuery) {
    pageQuery.clearFilter();
    const searchObj = this.searchForm.value;
    if (searchObj.username != null) {
      pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
    }
    if (searchObj.orderNo != null) {
      pageQuery.addOnlyFilter('orderNo', searchObj.orderNo, 'contains');
    }
    if (searchObj.createTime != null) {
      pageQuery.addFilter('createTime', this.startTimes, 'gte');
      pageQuery.addFilter('createTime', this.endTimes, 'lte');
    }
    if (searchObj.consignee != null) {
      pageQuery.addOnlyFilter('consignee', searchObj.consignee, 'contains');
    }
    if (searchObj.consigneePhone != null) {
      pageQuery.addOnlyFilter('consigneePhone', searchObj.consigneePhone, 'contains');
    }
    return pageQuery;
  }

  deliver() {
    this.getData();
  }

  changeRange(dates: Date[]) {
    if (dates != null) {
      if (dates.length != 0) {
        let split = dates[0].toLocaleDateString().split('/');
        let split1 = dates[1].toLocaleDateString().split('/');
        this.startTimes = split[0] + "-" + split[1] + "-" + split[2]
        this.endTimes = split1[0] + "-" + split1[1] + "-" + split1[2] + " " + "23:59:59"
      } else {
        this.searchForm.patchValue({
          createTime: null
        })
        this.searchData();
      }
    }
  }

}
