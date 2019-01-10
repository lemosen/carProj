import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ConsumeRecordService} from '../../../services/consume-record.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-consume-record',
    templateUrl: './list-consume-record.component.html',
    styleUrls: ['./list-consume-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListConsumeRecordComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private consumeRecordService: ConsumeRecordService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, consumeRecordService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            username: null,
            tradeNo: [null],
            orderNo: [null],
            consignee: [null],
            payAmount: [null],
            finishTime: [null],
            remark: [null],
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
            member: {
                id: null,
                username: null,
            },
            tradeNo: null,
            orderNo: null,
            consignee: null,
            payAmount: null,
            finishTime: null,
            remark: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.username!=null) {
            pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
            }
        return pageQuery;
    }


}
