import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ExpressTemplateService} from '../../../services/express-template.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-express-template',
    templateUrl: './list-express-template.component.html',
    styleUrls: ['./list-express-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListExpressTemplateComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private expressTemplateService: ExpressTemplateService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, expressTemplateService);
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
            printWidth: [null],
            printHigh: [null],
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
            templateNo: null,
            templateName: null,
            printWidth: null,
            printHigh: null,
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
        if (searchObj.templateNo!=null) {
            pageQuery.addOnlyFilter('templateNo', searchObj.templateNo, 'contains');
        }
        if (searchObj.templateName!=null) {
            pageQuery.addOnlyFilter('templateName', searchObj.templateName, 'contains');
        }
        return pageQuery;
    }


}
