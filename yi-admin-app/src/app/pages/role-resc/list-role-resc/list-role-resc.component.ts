


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {RoleRescService} from '../../../services/role-resc.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-role-resc',
    templateUrl: './list-role-resc.component.html',
    styleUrls: ['./list-role-resc.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRoleRescComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private roleRescService: RoleRescService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, roleRescService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        roleId:[null],
        rescId:[null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
        roleId:null,
        rescId:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.provinceId) {
     pageQuery.addOnlyFilter('roleId', searchObj.roleId, 'eq');
     pageQuery.addOnlyFilter('rescId', searchObj.rescId, 'eq');

        }
        return pageQuery;
    }


}
