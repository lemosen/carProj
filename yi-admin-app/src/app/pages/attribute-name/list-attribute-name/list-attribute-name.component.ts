import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AttributeNameService} from '../../../services/attribute-name.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-attribute-name',
    templateUrl: './list-attribute-name.component.html',
    styleUrls: ['./list-attribute-name.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAttributeNameComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private attributeNameService: AttributeNameService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, attributeNameService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            attributeGroup: [null],
            attrName: [null],
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
            attributeGroup: null,
            attrName: null,
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
        /*if (searchObj.attributeGroup.groupName!=null) {
            pageQuery.addOnlyFilter('attributeGroup.groupName', searchObj.attrName, 'contains');
        }*/
        if (searchObj.attrName!=null) {
            pageQuery.addOnlyFilter('attrName', searchObj.attrName, 'contains');
        }
        if (searchObj.createTime!=null) {
            pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
        }
        return pageQuery;
    }


}
