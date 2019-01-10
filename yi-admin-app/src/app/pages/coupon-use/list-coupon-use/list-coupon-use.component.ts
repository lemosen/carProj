


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {CouponUseService} from '../../../services/coupon-use.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-coupon-use',
    templateUrl: './list-coupon-use.component.html',
    styleUrls: ['./list-coupon-use.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListCouponUseComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private couponUseService: CouponUseService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, couponUseService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        couponId:[null],
        couponNo:[null],
        couponReceiveId:[null],
        parValue:[null],
        use:[null],
        surplus:[null],
        memberId:[null],
        memberPhone:[null],
        useTime:[null],
        orderId:[null],
        orderNo:[null],
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
        couponId:null,
        couponNo:null,
        couponReceiveId:null,
        parValue:null,
        use:null,
        surplus:null,
        memberId:null,
        memberPhone:null,
        useTime:null,
        orderId:null,
        orderNo:null,
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
     pageQuery.addOnlyFilter('couponId', searchObj.couponId, 'eq');
     pageQuery.addOnlyFilter('couponNo', searchObj.couponNo, 'eq');
     pageQuery.addOnlyFilter('couponReceiveId', searchObj.couponReceiveId, 'eq');
     pageQuery.addOnlyFilter('parValue', searchObj.parValue, 'eq');
     pageQuery.addOnlyFilter('use', searchObj.use, 'eq');
     pageQuery.addOnlyFilter('surplus', searchObj.surplus, 'eq');
     pageQuery.addOnlyFilter('memberId', searchObj.memberId, 'eq');
     pageQuery.addOnlyFilter('memberPhone', searchObj.memberPhone, 'eq');
     pageQuery.addOnlyFilter('useTime', searchObj.useTime, 'eq');
     pageQuery.addOnlyFilter('orderId', searchObj.orderId, 'eq');
     pageQuery.addOnlyFilter('orderNo', searchObj.orderNo, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
