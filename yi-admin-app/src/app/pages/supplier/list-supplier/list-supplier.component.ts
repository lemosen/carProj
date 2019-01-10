import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SupplierService} from '../../../services/supplier.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-supplier',
    templateUrl: './list-supplier.component.html',
    styleUrls: ['./list-supplier.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSupplierComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private supplierService: SupplierService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, supplierService);
        this.buildForm();
    }

    menus = [
        {name: "全部", code: 0, value: ""},
        {name: "已启用", code: 1, value: 0},
        {name: "已禁用", code: 2, value: 1},
    ];
    selectMenu: number = 0

    onItemClick(i) {
        this.pageQuery.clearFilter();
        let elementsByClassName: any = document.getElementsByClassName("filter");
        for (let elementsByClassNameKey in elementsByClassName) {
            elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
        }
        this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('state');
        this.getDatas();
        this.selectMenu = i;
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            user: {
                id:null,
                username:null,
            },
            guid: [null],
            userId: [null],
            supplierNo: [null],
            supplierName: [null],
            phone: [null],
            password: [null],
            state: [null],
            contact: [null],
            contactPhone: [null],
            province: [null],
            city: [null],
            district: [null],
            address: [null],
            remark: [null],
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
            user: {
                id:null,
                username:null,
            },
            supplierNo: null,
            supplierName: null,
            phone: null,
            password: null,
            state: null,
            contact: null,
            contactPhone: null,
            province: null,
            city: null,
            district: null,
            address: null,
            remark: null,
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
        if (searchObj.supplierName!=null) {
            pageQuery.addOnlyFilter('supplierName', searchObj.supplierName, 'contains');
        }
        if (searchObj.supplierNo!=null) {
            pageQuery.addOnlyFilter('supplierNo', searchObj.supplierNo, 'contains');
        }
        return pageQuery;
    }


    disable(recommendId){
        this.dialogService.confirm('提示', '确认禁用吗？').then(result => {
            if (result) {
                this.supplierService.banKai(recommendId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        })
    }

    enable(recommendId){
        this.dialogService.confirm('提示', '确认启用吗？').then(result => {
            if (result) {
                this.supplierService.banKai(recommendId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        })
    }



}
