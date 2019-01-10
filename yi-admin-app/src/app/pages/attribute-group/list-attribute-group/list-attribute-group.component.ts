import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AttributeGroupService} from '../../../services/attribute-group.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-attribute-group',
    templateUrl: './list-attribute-group.component.html',
    styleUrls: ['./list-attribute-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAttributeGroupComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private attributeGroupService: AttributeGroupService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, attributeGroupService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            groupNo: [null],
            groupName: [null],
            imgPath: [null],
            sort: [null],
            state: [null],
            createTime: [null],
            remark: [null],
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
            groupNo: null,
            groupName: null,
            imgPath: null,
            sort: null,
            state: null,
            createTime: null,
            remark: null,
            deleted: null,
            delTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.groupName != null) {
            pageQuery.addOnlyFilter('groupName', searchObj.groupName, 'contains');
        }
        if (searchObj.createTime != null) {
            pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
        }
        return pageQuery;
    }


}
