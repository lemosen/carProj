import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FreightTemplateService} from '../../../services/freight-template.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-freight-template',
    templateUrl: './list-freight-template.component.html',
    styleUrls: ['./list-freight-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListFreightTemplateComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private freightTemplateService: FreightTemplateService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, freightTemplateService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            templateNo: [null],
            templateName: [null],
            chargeMode: [null],
            state: [null],
            presetWeight: [null],
            presetFee: [null],
            extraWeight: [null],
            extraFee: [null],
            province: [null],
            city: [null],
            district: [null],
            address: [null],
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
            templateNo: null,
            templateName: null,
            chargeMode: null,
            state: null,
            presetWeight: null,
            presetFee: null,
            extraWeight: null,
            extraFee: null,
            province: null,
            city: null,
            district: null,
            address: null,
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
        if (searchObj.templateNo!=null) {
            pageQuery.addOnlyFilter('templateNo', searchObj.templateNo, 'contains');
        }
        if (searchObj.templateName!=null) {
            pageQuery.addOnlyFilter('templateName', searchObj.templateName, 'contains');
        }
        return pageQuery;
    }


}
