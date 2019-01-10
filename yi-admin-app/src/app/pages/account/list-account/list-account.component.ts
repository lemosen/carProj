


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AccountService} from '../../../services/account.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-account',
    templateUrl: './list-account.component.html',
    styleUrls: ['./list-account.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAccountComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private accountService: AccountService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, accountService);
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
        orderQuantity:[null],
        consumeAmount:[null],
        balance:[null],
        freezeAmount:[null],
        integral:[null],
        residualIntegral:[null],
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
        orderQuantity:null,
        consumeAmount:null,
        balance:null,
        freezeAmount:null,
        integral:null,
        residualIntegral:null,
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
     pageQuery.addOnlyFilter('orderQuantity', searchObj.orderQuantity, 'eq');
     pageQuery.addOnlyFilter('consumeAmount', searchObj.consumeAmount, 'eq');
     pageQuery.addOnlyFilter('balance', searchObj.balance, 'eq');
     pageQuery.addOnlyFilter('freezeAmount', searchObj.freezeAmount, 'eq');
     pageQuery.addOnlyFilter('integral', searchObj.integral, 'eq');
     pageQuery.addOnlyFilter('residualIntegral', searchObj.residualIntegral, 'eq');
     pageQuery.addOnlyFilter('remark', searchObj.remark, 'eq');

        }
        return pageQuery;
    }


}
