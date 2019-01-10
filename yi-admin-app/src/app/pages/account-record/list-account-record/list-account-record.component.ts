


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AccountRecordService} from '../../../services/account-record.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-account-record',
    templateUrl: './list-account-record.component.html',
    styleUrls: ['./list-account-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAccountRecordComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private accountRecordService: AccountRecordService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, accountRecordService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        memberId:[null],
        operateType:[null],
        serialNo:[null],
        tradeAmount:[null],
        balance:[null],
        tradeType:[null],
        tradeMode:[null],
        tradeTime:[null],
        remark:[null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
        id:null,
        guid:null,
        memberId:null,
        operateType:null,
        serialNo:null,
        tradeAmount:null,
        balance:null,
        tradeType:null,
        tradeMode:null,
        tradeTime:null,
        remark:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.provinceId) {
     pageQuery.addOnlyFilter('id', searchObj.id, 'eq');
     pageQuery.addOnlyFilter('guid', searchObj.guid, 'eq');
     pageQuery.addOnlyFilter('memberId', searchObj.memberId, 'eq');
     pageQuery.addOnlyFilter('operateType', searchObj.operateType, 'eq');
     pageQuery.addOnlyFilter('serialNo', searchObj.serialNo, 'eq');
     pageQuery.addOnlyFilter('tradeAmount', searchObj.tradeAmount, 'eq');
     pageQuery.addOnlyFilter('balance', searchObj.balance, 'eq');
     pageQuery.addOnlyFilter('tradeType', searchObj.tradeType, 'eq');
     pageQuery.addOnlyFilter('tradeMode', searchObj.tradeMode, 'eq');
     pageQuery.addOnlyFilter('tradeTime', searchObj.tradeTime, 'eq');
     pageQuery.addOnlyFilter('remark', searchObj.remark, 'eq');

        }
        return pageQuery;
    }


}
