


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {ReturnReasonService} from "../../../services/return-reason.service";

@Component({
    selector: 'app-list-return-reason',
    templateUrl: './list-return-reason.component.html',
    styleUrls: ['./list-return-reason.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListReturnReasonComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private returnReasonService: ReturnReasonService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, returnReasonService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        reasonType:[null],
        sort:[null],
        state:[null],
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
        reasonType:null,
        sort:null,
        state:null,
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
     pageQuery.addOnlyFilter('reasonType', searchObj.reasonType, 'eq');
     pageQuery.addOnlyFilter('sort', searchObj.sort, 'eq');
     pageQuery.addOnlyFilter('state', searchObj.state, 'eq');
     pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
