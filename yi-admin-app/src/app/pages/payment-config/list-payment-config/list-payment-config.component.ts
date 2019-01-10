


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {PaymentConfigService} from '../../../services/payment-config.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-payment-config',
    templateUrl: './list-payment-config.component.html',
    styleUrls: ['./list-payment-config.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListPaymentConfigComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private paymentConfigService: PaymentConfigService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, paymentConfigService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        payType:[null],
        appId:[null],
        method:[null],
        charset:[null],
        signType:[null],
        sign:[null],
        version:[null],
        notifyUrl:[null],
        mchId:[null],
        appKey:[null],
        appSecret:[null],
        createTime:[null],
        deleted:[null],
        delTime:[null],
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
        payType:null,
        appId:null,
        method:null,
        charset:null,
        signType:null,
        sign:null,
        version:null,
        notifyUrl:null,
        mchId:null,
        appKey:null,
        appSecret:null,
        createTime:null,
        deleted:null,
        delTime:null,
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
     pageQuery.addOnlyFilter('payType', searchObj.payType, 'eq');
     pageQuery.addOnlyFilter('appId', searchObj.appId, 'eq');
     pageQuery.addOnlyFilter('method', searchObj.method, 'eq');
     pageQuery.addOnlyFilter('charset', searchObj.charset, 'eq');
     pageQuery.addOnlyFilter('signType', searchObj.signType, 'eq');
     pageQuery.addOnlyFilter('sign', searchObj.sign, 'eq');
     pageQuery.addOnlyFilter('version', searchObj.version, 'eq');
     pageQuery.addOnlyFilter('notifyUrl', searchObj.notifyUrl, 'eq');
     pageQuery.addOnlyFilter('mchId', searchObj.mchId, 'eq');
     pageQuery.addOnlyFilter('appKey', searchObj.appKey, 'eq');
     pageQuery.addOnlyFilter('appSecret', searchObj.appSecret, 'eq');
     pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
