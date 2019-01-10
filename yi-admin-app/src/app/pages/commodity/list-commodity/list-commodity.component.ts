import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {CommodityService} from '../../../services/commodity.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SupplierService} from "../../../services/supplier.service";
import {SUCCESS} from "../../models/constants.model";
import {Supplier} from "../../models/original/supplier.model";

@Component({
    selector: 'app-list-commodity',
    templateUrl: './list-commodity.component.html',
    styleUrls: ['./list-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListCommodityComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public supplierService: SupplierService, public route: ActivatedRoute, public router: Router, private commodityService: CommodityService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, commodityService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    menus = [
        {name: "全部商品", code: 0, value: ""},
        {name: "已上架", code: 1, value: 1},
        {name: "仓库中", code: 2, value: 2},
    ];

    selectMenu: number = 0

    onItemClick(i) {
        this.pageQuery.clearFilter();
        let elementsByClassName: any = document.getElementsByClassName("filter");
        for (let elementsByClassNameKey in elementsByClassName) {
            elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
        }
        this.pageQuery.addOnlyFilter('shelf', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('shelf');
        this.getDatas();
        this.selectMenu = i;
    }


    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            commodityNo: [null],
            commodityName: [null],
            supplierName:[null],
            supplier: {
                id: null,
                supplierName: null
            },
            imgPath: [null],
            sort: [null],
            distribution: [null],
            commissionRate: [null],
            freightSet: [null],
            unifiedFreight: [null],
            freightTemplate: [null],
            stockSet: [null],
            volume: [null],
            weight: [null],
            shelf: [null],
            description: [null],
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
            commodityNo: null,
            commodityName: null,
            supplierName:null,
            supplier: {
                id: null,
                supplierName: null
            },
            sort: null,
            imgPath: null,
            distribution: null,
            commissionRate: null,
            freightSet: null,
            unifiedFreight: null,
            freightTemplate: null,
            stockSet: null,
            volume: null,
            weight: null,
            shelf: null,
            description: null,
            createTime: null,
            deleted: null,
            delTime: null,
        });
        this.pageQuery.clearFilter();
        // this.select.reset()
        this.searchData();
    }

    onTheShelf(commodityId){
        this.dialogService.confirm('提示', '确认上架吗？').then((result) => {
            if (result) {
                this.commodityService.upAndDown(commodityId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        /*this.router.navigate(['/pages/member/list']);*/
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        }, () => {
        });
    }



    lowerFrame(commodityId){
        this.dialogService.confirm('提示', '确认下架吗？').then((result) => {
            if (result) {
                this.commodityService.upAndDown(commodityId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        /*this.router.navigate(['/pages/member/list']);*/
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        }, () => {
        });
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.commodityName != null) {
            pageQuery.addOnlyFilter('commodityName', searchObj.commodityName, 'contains');
        }
        if (searchObj.supplierName != null) {
            pageQuery.addOnlyFilter('supplier.supplierName', searchObj.supplierName, 'contains');
        }
        return pageQuery;
    }

}
