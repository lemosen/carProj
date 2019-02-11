import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {RewardService} from '../../../services/reward.service';
import {RewardListVo} from '../../../models/domain/listVo/reward-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";

@Component({
  selector: 'list-reward',
  templateUrl: './list-reward.component.html',
  styleUrls: ['./list-reward.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListRewardComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  rewards: Array<RewardListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private rewardService: RewardService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      name: [null],
      rewardType: [null],
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
    this.rewardService.query(this.pageQuery).subscribe(response => {
      this.rewards = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      name: null,
      rewardType: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(rewardId) {
    this.rewardService.removeById(rewardId).subscribe(response => {
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
    if (searchObj.name != null) {
      pageQuery.addOnlyFilter('name', searchObj.name, 'contains');
    }
    if (searchObj.rewardType != null) {
      pageQuery.addOnlyFilter('rewardType', searchObj.rewardType, 'eq');
    }
    return pageQuery;
  }

  rewardTypes = [
    {id: 1, name: "积分"},
    {id: 2, name: "报考"},
    {id: 3, name: "优惠券"},
  ]

}
