import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SupplierCheckAccountService} from '../../../services/supplier-check-account.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-supplier-check-account',
    templateUrl: './list-supplier-check-account.component.html',
    styleUrls: ['./list-supplier-check-account.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSupplierCheckAccountComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private supplierCheckAccountService: SupplierCheckAccountService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, supplierCheckAccountService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            supplier: [null],
            supplierName: [null],
            saleOrderId: [null],
            saleOrderNo: [null],
            orderTime: [null],
            productId: [null],
            productNo: [null],
            productName: [null],
            supplyPrice: [null],
            quantity: [null],
            settlementAmount: [null],
            settlementTime: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            supplierId: null,
            supplierName: null,
            saleOrderId: null,
            saleOrderNo: null,
            orderTime: null,
            productId: null,
            productNo: null,
            productName: null,
            supplyPrice: null,
            quantity: null,
            settlementAmount: null,
            settlementTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.supplierName!=null) {
            pageQuery.addOnlyFilter('supplierName', searchObj.supplierName, 'contains');
        }
        return pageQuery;
    }


}
