import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AttributeValueService} from '../../../services/attribute-value.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-attribute-value',
    templateUrl: './list-attribute-value.component.html',
    styleUrls: ['./list-attribute-value.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAttributeValueComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private attributeValueService: AttributeValueService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, attributeValueService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            attrValue: [null],
            state: [null],
            createTime: [null],
            deleted: [null],
            delTime: [null],
            attrNameId: [null],
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
            attrValue: null,
            state: null,
            createTime: null,
            deleted: null,
            delTime: null,
            attrNameId: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.attrValue != null) {
            pageQuery.addOnlyFilter('attrValue', searchObj.attrValue, 'contains');
        }
        if (searchObj.createTime != null) {
            pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
        }
        return pageQuery;
    }


}
