


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UserRoleService} from '../../../services/user-role.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-user-role',
    templateUrl: './list-user-role.component.html',
    styleUrls: ['./list-user-role.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListUserRoleComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private userRoleService: UserRoleService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, userRoleService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        userId:[null],
        roleId:[null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
        userId:null,
        roleId:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.provinceId) {
     pageQuery.addOnlyFilter('userId', searchObj.userId, 'eq');
     pageQuery.addOnlyFilter('roleId', searchObj.roleId, 'eq');

        }
        return pageQuery;
    }


}
