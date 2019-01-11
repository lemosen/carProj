
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import { PrizeService } from '../../../services/prize.service';
import { PrizeListVo } from '../../../models/domain/listVo/prize-list-vo.model';
import {PageQuery} from "../../../models/page-query.model";

@Component({
    selector: 'list-prize',
    templateUrl: './list-prize.component.html',
    styleUrls: ['./list-prize.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListPrizeComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    prizes: Array<PrizeListVo>;

    expandForm = false;


    constructor(public route: ActivatedRoute, public router: Router, private prizeService: PrizeService, public msg: NzMessageService,
        private fb: FormBuilder,private modalService: NzModalService) {
        this.buildForm();
    }

    ngOnInit() {
        this.searchData();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        name:[null],
        prizeType:[null],
        });
    }

    sort(sort: { key: string, value: string }): void {
        this.pageQuery.addSort(sort.key,sort.value)
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
        this.prizeService.query(this.pageQuery).subscribe(response => {
            this.prizes=response['content'];
            this.pageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误'+ error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({
        name:null,
        prizeType:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    remove(prizeId) {
        this.prizeService.removeById(prizeId).subscribe(response => {
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
            if (searchObj.prizeType != null) {
                pageQuery.addOnlyFilter('prizeType', searchObj.prizeType, 'contains');
            }
        return pageQuery;
    }

  prizeTypes = [
    {id: 1, name: "邀请"},
    {id: 2, name: "评论"},
    {id: 3, name: "连续签到"},
  ]

}
