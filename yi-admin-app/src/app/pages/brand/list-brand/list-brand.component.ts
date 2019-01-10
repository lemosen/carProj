


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {BrandService} from '../../../services/brand.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-brand',
    templateUrl: './list-brand.component.html',
    styleUrls: ['./list-brand.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListBrandComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private brandService: BrandService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, brandService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        brandNo:[null],
        cnName:[null],
        enName:[null],
        imgPath:[null],
        state:[null],
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
        brandNo:null,
        cnName:null,
        enName:null,
        imgPath:null,
        state:null,
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
     pageQuery.addOnlyFilter('brandNo', searchObj.brandNo, 'eq');
     pageQuery.addOnlyFilter('cnName', searchObj.cnName, 'eq');
     pageQuery.addOnlyFilter('enName', searchObj.enName, 'eq');
     pageQuery.addOnlyFilter('imgPath', searchObj.imgPath, 'eq');
     pageQuery.addOnlyFilter('state', searchObj.state, 'eq');
     pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
