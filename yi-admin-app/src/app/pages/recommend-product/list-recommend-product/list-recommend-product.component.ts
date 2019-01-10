


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {RecommendProductService} from '../../../services/recommend-product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-recommend-product',
    templateUrl: './list-recommend-product.component.html',
    styleUrls: ['./list-recommend-product.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRecommendProductComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private recommendProductService: RecommendProductService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, recommendProductService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        recommendId:[null],
        productId:[null],
        productCode:[null],
        productName:[null],
        imgPath:[null],
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
        recommendId:null,
        productId:null,
        productCode:null,
        productName:null,
        imgPath:null,
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
     pageQuery.addOnlyFilter('recommendId', searchObj.recommendId, 'eq');
     pageQuery.addOnlyFilter('productId', searchObj.productId, 'eq');
     pageQuery.addOnlyFilter('productCode', searchObj.productCode, 'eq');
     pageQuery.addOnlyFilter('productName', searchObj.productName, 'eq');
     pageQuery.addOnlyFilter('imgPath', searchObj.imgPath, 'eq');

        }
        return pageQuery;
    }


}
