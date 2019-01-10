import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {CouponService} from '../../../services/coupon.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-coupon',
    templateUrl: './list-coupon.component.html',
    styleUrls: ['./list-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListCouponComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private couponService: CouponService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, couponService);
        this.buildForm();
    }

    ngOnInit() {
        this.pageQuery.addOnlyFilter('couponType', this.type, 'eq');
        this.getDatas();
    }

    couponType = [
        {
        key: "满减券",
        value: 1
    },{
        key: "买送券",
        value: 2
    }];
    type=1;
    selectValue(value) {
        this.type=value;
       /* this.pageQuery.addOnlyFilter('couponType', value, 'contains');*/
     /*   this.searchForm.get(type).setValue(value);*/
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            couponNo: [null],
            couponName: [null],
            couponType: [null],
            parValue: [null],
            quantity: [null],
            useQuantity: [null],
            useCondition: [null],
            receiveMode: [null],
            memberLevelId: [null],
            limit: [null],
            validType: [null],
            startTime: [null],
            endTime: [null],
            fixedDay: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            id: null,
            couponNo: null,
            couponName: null,
            couponType: null,
            parValue: null,
            quantity: null,
            useQuantity: null,
            useCondition: null,
            receiveMode: null,
            memberLevelId: null,
            limit: null,
            validType: null,
            startTime: null,
            endTime: null,
            fixedDay: null,
        });
        this.pageQuery.clearFilter();
        this.pageQuery.addOnlyFilter('couponType', this.type, 'eq');
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        pageQuery.addOnlyFilter('couponType',this.type, 'eq')
        if (searchObj.couponName!=null) {
            pageQuery.addOnlyFilter('couponName', searchObj.couponName, 'contains');
        }
        return pageQuery;
    }


}
