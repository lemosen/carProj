


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ProductService} from '../../../services/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-product',
    templateUrl: './list-product.component.html',
    styleUrls: ['./list-product.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListProductComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private productService: ProductService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, productService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        productNo:[null],
        productName:[null],
        commodityId:[null],
        categoryId:[null],
        supplierId:[null],
        sort:[null],
        distribution:[null],
        commissionRate:[null],
        freightType:[null],
        unifiedFreight:[null],
        freightTemplateId:[null],
        stockSet:[null],
        volume:[null],
        weight:[null],
        shelf:[null],
        description:[null],
        attrName:[null],
        attrValue:[null],
        originalPrice:[null],
        currentPrice:[null],
        memberPrice:[null],
        sku:[null],
        stock:[null],
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
        productNo:null,
        productName:null,
        commodityId:null,
        categoryId:null,
        supplierId:null,
        sort:null,
        distribution:null,
        commissionRate:null,
        freightType:null,
        unifiedFreight:null,
        freightTemplateId:null,
        stockSet:null,
        volume:null,
        weight:null,
        shelf:null,
        description:null,
        attrName:null,
        attrValue:null,
        originalPrice:null,
        currentPrice:null,
        memberPrice:null,
        sku:null,
        stock:null,
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
     pageQuery.addOnlyFilter('productNo', searchObj.productNo, 'eq');
     pageQuery.addOnlyFilter('productName', searchObj.productName, 'eq');
     pageQuery.addOnlyFilter('commodityId', searchObj.commodityId, 'eq');
     pageQuery.addOnlyFilter('categoryId', searchObj.categoryId, 'eq');
     pageQuery.addOnlyFilter('supplierId', searchObj.supplierId, 'eq');
     pageQuery.addOnlyFilter('sort', searchObj.sort, 'eq');
     pageQuery.addOnlyFilter('distribution', searchObj.distribution, 'eq');
     pageQuery.addOnlyFilter('commissionRate', searchObj.commissionRate, 'eq');
     pageQuery.addOnlyFilter('freightType', searchObj.freightType, 'eq');
     pageQuery.addOnlyFilter('unifiedFreight', searchObj.unifiedFreight, 'eq');
     pageQuery.addOnlyFilter('freightTemplateId', searchObj.freightTemplateId, 'eq');
     pageQuery.addOnlyFilter('stockSet', searchObj.stockSet, 'eq');
     pageQuery.addOnlyFilter('volume', searchObj.volume, 'eq');
     pageQuery.addOnlyFilter('weight', searchObj.weight, 'eq');
     pageQuery.addOnlyFilter('shelf', searchObj.shelf, 'eq');
     pageQuery.addOnlyFilter('description', searchObj.description, 'eq');
     pageQuery.addOnlyFilter('attrName', searchObj.attrName, 'eq');
     pageQuery.addOnlyFilter('attrValue', searchObj.attrValue, 'eq');
     pageQuery.addOnlyFilter('originalPrice', searchObj.originalPrice, 'eq');
     pageQuery.addOnlyFilter('currentPrice', searchObj.currentPrice, 'eq');
     pageQuery.addOnlyFilter('memberPrice', searchObj.memberPrice, 'eq');
     pageQuery.addOnlyFilter('sku', searchObj.sku, 'eq');
     pageQuery.addOnlyFilter('stock', searchObj.stock, 'eq');
     pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
