


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ShippingAddressService} from '../../../services/shipping-address.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-shipping-address',
    templateUrl: './list-shipping-address.component.html',
    styleUrls: ['./list-shipping-address.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListShippingAddressComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private shippingAddressService: ShippingAddressService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, shippingAddressService);
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
        fullName:[null],
        phone:[null],
        province:[null],
        city:[null],
        district:[null],
        address:[null],
        preferred:[null],
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
        memberId:null,
        fullName:null,
        phone:null,
        province:null,
        city:null,
        district:null,
        address:null,
        preferred:null,
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
     pageQuery.addOnlyFilter('memberId', searchObj.memberId, 'eq');
     pageQuery.addOnlyFilter('fullName', searchObj.fullName, 'eq');
     pageQuery.addOnlyFilter('phone', searchObj.phone, 'eq');
     pageQuery.addOnlyFilter('province', searchObj.province, 'eq');
     pageQuery.addOnlyFilter('city', searchObj.city, 'eq');
     pageQuery.addOnlyFilter('district', searchObj.district, 'eq');
     pageQuery.addOnlyFilter('address', searchObj.address, 'eq');
     pageQuery.addOnlyFilter('preferred', searchObj.preferred, 'eq');
     pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
