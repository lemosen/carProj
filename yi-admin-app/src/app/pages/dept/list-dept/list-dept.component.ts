import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {DeptService} from '../../../services/dept.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-dept',
    templateUrl: './list-dept.component.html',
    styleUrls: ['./list-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListDeptComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, public deptService: DeptService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, deptService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            parentDeptName:[null],
            parent: {
                id: null,
                deptName: null
            },
            deptNo: [null],
            deptName: [null],
            description: [null],
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
            parentDeptName:null,
            parent: {
                id: null,
                deptName: null
            },
            deptNo: null,
            deptName: null,
            description: null,
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
        if (searchObj.deptName!=null) {
            pageQuery.addOnlyFilter('deptName', searchObj.deptName, 'contains');
        }
        if (searchObj.parentDeptName!=null) {
            pageQuery.addOnlyFilter('parent.deptName', searchObj.parentDeptName, 'contains');
        }
        return pageQuery;
    }


}
