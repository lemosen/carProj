import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SupplierWithdrawService} from "../../../services/supplier-withdraw.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";
import {PageQuery} from "../../models/page-query.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
    selector: 'app-list-supplier-record',
    templateUrl: './list-supplier-record.component.html',
    styleUrls: ['./list-supplier-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSupplierRecordComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private supplierWithdrawService: SupplierWithdrawService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, supplierWithdrawService);
        this.buildForm();
    }

    ngOnInit(){
        this.pageQuery.addOnlyFilter('state',3, 'eq');
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            supplierId:[null],
            supplierName:[null],
            amount:[null],
            cardType:[null],
            cardNo:[null],
            payee:[null],
            state:[null],
            errorDescription:[null],
            applyTime:[null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.pageQuery.addOnlyFilter('state',3, 'eq');
        this.getDatas();
    }


    clearSearch() {
        this.searchForm.reset({
            supplierName:null,
            payee:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery){
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.supplierName!=null) {
            pageQuery.addOnlyFilter('supplierName', searchObj.supplierName, 'contains');
        }
        if (searchObj.payee!=null) {
            pageQuery.addOnlyFilter('payee', searchObj.payee, 'contains');
        }
        return pageQuery;
    }

}
