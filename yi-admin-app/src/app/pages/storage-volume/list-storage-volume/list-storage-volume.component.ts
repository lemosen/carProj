


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {CouponService} from '../../../services/coupon.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-storage-volume',
    templateUrl: './list-storage-volume.component.html',
    styleUrls: ['./list-storage-volume.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListStorageVolumeComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private couponService: CouponService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, couponService);
        this.buildForm();
    }

    ngOnInit() {
        this.pageQuery.addOnlyFilter('couponType',3, 'eq')
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        couponNo:[null],
        couponName:[null],
        couponType:[null],
        parValue:[null],
        quantity:[null],
        useQuantity:[null],
        useCondition:[null],
        receiveMode:[null],
        memberLevelId:[null],
        limit:[null],
        validType:[null],
        startTime:[null],
        endTime:[null],
        fixedDay:[null],
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
        couponNo:null,
        couponName:null,
        couponType:null,
        parValue:null,
        quantity:null,
        useQuantity:null,
        useCondition:null,
        receiveMode:null,
        memberLevelId:null,
        limit:null,
        validType:null,
        startTime:null,
        endTime:null,
        fixedDay:null,
        deleted:null,
        delTime:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        pageQuery.addOnlyFilter('couponType',3, 'eq')
        if (searchObj.couponName!=null) {
            pageQuery.addOnlyFilter('couponName', searchObj.couponName, 'contains');
        }
        return pageQuery;
    }


}
