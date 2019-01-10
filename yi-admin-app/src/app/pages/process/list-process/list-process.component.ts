


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ProcessService} from '../../../services/process.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-process',
    templateUrl: './list-process.component.html',
    styleUrls: ['./list-process.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListProcessComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private processService: ProcessService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, processService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        refundOrderId:[null],
        returnOrderId:[null],
        userId:[null],
        username:[null],
        processType:[null],
        processTime:[null],
        remark:[null],
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
        refundOrderId:null,
        returnOrderId:null,
        userId:null,
        username:null,
        processType:null,
        processTime:null,
        remark:null,
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
     pageQuery.addOnlyFilter('refundOrderId', searchObj.refundOrderId, 'eq');
     pageQuery.addOnlyFilter('returnOrderId', searchObj.returnOrderId, 'eq');
     pageQuery.addOnlyFilter('userId', searchObj.userId, 'eq');
     pageQuery.addOnlyFilter('username', searchObj.username, 'eq');
     pageQuery.addOnlyFilter('processType', searchObj.processType, 'eq');
     pageQuery.addOnlyFilter('processTime', searchObj.processTime, 'eq');
     pageQuery.addOnlyFilter('remark', searchObj.remark, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
