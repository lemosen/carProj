import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {TransactionRecordService} from '../../../services/transaction-record.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";
import {PageQuery} from "../../models/page-query.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
    selector: 'app-list-transaction-record',
    templateUrl: './list-transaction-record.component.html',
    styleUrls: ['./list-transaction-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListTransactionRecordComponent extends CommonList implements OnInit {


    commonForm: FormGroup;

    constructor(public route: ActivatedRoute, private fb: FormBuilder,public router: Router, private transactionRecordService: TransactionRecordService, public dialogService: DialogsService) {
        super(route, router, dialogService, transactionRecordService);
    }

    ngOnInit() {
        this.getDatas();
    }

    type=1;
    selectValue(value) {
        this.type=value;
        /* this.pageQuery.addOnlyFilter('couponType', value, 'contains');*/
        /*   this.searchForm.get(type).setValue(value);*/
    }

    buildForm() {
        this.commonForm = this.fb.group({
            recordPrice: [null],
            recordType: [null],
            recordMode: [null],
            remark: [null],
            no: [null],

        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.commonForm.reset({
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
        const searchObj = this.commonForm.value;
        pageQuery.addOnlyFilter('couponType',this.type, 'eq')
        if (searchObj.couponName!=null) {
            pageQuery.addOnlyFilter('couponName', searchObj.couponName, 'contains');
        }
        return pageQuery;
    }

}
