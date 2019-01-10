import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {UserService} from '../../../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {DeptService} from "../../../services/dept.service";

@Component({
    selector: 'app-list-user',
    templateUrl: './list-user.component.html',
    styleUrls: ['./list-user.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListUserComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public deptService: DeptService, public route: ActivatedRoute, public router: Router, private userService: UserService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, userService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            deptName:[null],
            dept: {
                id: null,
                deptName: null
            },
            username: [null],
            password: [null],
            fullName: [null],
            phone: [null],
            email: [null],
            jobNo: [null],
            avatar: [null],
            state: [null],
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
            deptName:null,
            dept: {
                id: null,
                deptName: null
            },
            username: null,
            password: null,
            fullName: null,
            phone: null,
            email: null,
            jobNo: null,
            avatar: null,
            state: null,
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
        if (searchObj.deptName != null) {
            pageQuery.addOnlyFilter('dept.deptName', searchObj.deptName, 'contains');
        }
        if (searchObj.username != null) {
            pageQuery.addOnlyFilter('username', searchObj.username, 'contains');
        }
        if (searchObj.fullName != null) {
            pageQuery.addOnlyFilter('fullName', searchObj.fullName, 'contains');
        }
        if (searchObj.jobNo != null) {
            pageQuery.addOnlyFilter('jobNo', searchObj.jobNo, 'contains');
        }
        return pageQuery;
    }


}
