


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MemberLevelService} from '../../../services/member-level.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-member-level',
    templateUrl: './list-member-level.component.html',
    styleUrls: ['./list-member-level.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListMemberLevelComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private memberLevelService: MemberLevelService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, memberLevelService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        name:[null],
        quantity:[null],
        growthValue:[null],
        discount:[null],
        initial:[null],
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
        name:null,
        quantity:null,
        growthValue:null,
        discount:null,
        initial:null,
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
     pageQuery.addOnlyFilter('name', searchObj.name, 'eq');
     pageQuery.addOnlyFilter('quantity', searchObj.quantity, 'eq');
     pageQuery.addOnlyFilter('growthValue', searchObj.growthValue, 'eq');
     pageQuery.addOnlyFilter('discount', searchObj.discount, 'eq');
     pageQuery.addOnlyFilter('initial', searchObj.initial, 'eq');
     pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
