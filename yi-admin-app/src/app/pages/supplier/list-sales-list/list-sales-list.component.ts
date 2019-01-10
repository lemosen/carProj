import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SupplierService} from '../../../services/supplier.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SalesListModel} from "../../../../../../v3/src/domain/bo/sales-list.model";
import {Params} from "@angular/router";

@Component({
    selector: 'app-list-sales-list',
    templateUrl: './list-sales-list.component.html',
    styleUrls: ['./list-sales-list.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSalesListComponent extends CommonList implements OnInit {

    searchForm: FormGroup;



    constructor(public route: ActivatedRoute, public router: Router, private supplierService: SupplierService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, supplierService);
        this.buildForm();
    }

    ngOnInit() {
        // this.getDatas();

        this.route.params.subscribe((params: ParamMap) => {
            this.getSales(params["objId"]);

        });

    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            userId: [null],
            supplierNo: [null],
            supplierName: [null],
            phone: [null],
            password: [null],
            state: [null],
            contact: [null],
            contactPhone: [null],
            province: [null],
            city: [null],
            district: [null],
            address: [null],
            remark: [null],
            createTime: [null],
            deleted: [null],
            delTime: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            id: null,
            guid: null,
            userId: null,
            supplierNo: null,
            supplierName: null,
            phone: null,
            password: null,
            state: null,
            contact: null,
            contactPhone: null,
            province: null,
            city: null,
            district: null,
            address: null,
            remark: null,
            createTime: null,
            deleted: null,
            delTime: null,
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
            pageQuery.addOnlyFilter('userId', searchObj.userId, 'eq');
            pageQuery.addOnlyFilter('supplierNo', searchObj.supplierNo, 'eq');
            pageQuery.addOnlyFilter('supplierName', searchObj.supplierName, 'eq');
            pageQuery.addOnlyFilter('phone', searchObj.phone, 'eq');
            pageQuery.addOnlyFilter('password', searchObj.password, 'eq');
            pageQuery.addOnlyFilter('state', searchObj.state, 'eq');
            pageQuery.addOnlyFilter('contact', searchObj.contact, 'eq');
            pageQuery.addOnlyFilter('contactPhone', searchObj.contactPhone, 'eq');
            pageQuery.addOnlyFilter('province', searchObj.province, 'eq');
            pageQuery.addOnlyFilter('city', searchObj.city, 'eq');
            pageQuery.addOnlyFilter('district', searchObj.district, 'eq');
            pageQuery.addOnlyFilter('address', searchObj.address, 'eq');
            pageQuery.addOnlyFilter('remark', searchObj.remark, 'eq');
            pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
            pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
            pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }

    salesList:[SalesListModel];
    getSales(id){
        this.supplierService.getSalesList(id).subscribe(response => {
            this.salesList = response.data;

        })
    }

}
