


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {OrderLogService} from '../../../services/order-log.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-order-log',
    templateUrl: './list-order-log.component.html',
    styleUrls: ['./list-order-log.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListOrderLogComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private orderLogService: OrderLogService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, orderLogService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        orderId:[null],
        orderNo:[null],
        state:[null],
        operateTime:[null],
        operator:[null],
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
        orderId:null,
        orderNo:null,
        state:null,
        operateTime:null,
        operator:null,
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
     pageQuery.addOnlyFilter('orderId', searchObj.orderId, 'eq');
     pageQuery.addOnlyFilter('orderNo', searchObj.orderNo, 'eq');
     pageQuery.addOnlyFilter('state', searchObj.state, 'eq');
     pageQuery.addOnlyFilter('operateTime', searchObj.operateTime, 'eq');
     pageQuery.addOnlyFilter('operator', searchObj.operator, 'eq');
     pageQuery.addOnlyFilter('remark', searchObj.remark, 'eq');

        }
        return pageQuery;
    }


}
