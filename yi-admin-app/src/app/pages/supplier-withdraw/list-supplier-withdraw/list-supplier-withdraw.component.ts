import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SupplierWithdrawService} from '../../../services/supplier-withdraw.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-supplier-withdraw',
    templateUrl: './list-supplier-withdraw.component.html',
    styleUrls: ['./list-supplier-withdraw.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSupplierWithdraw extends CommonList implements OnInit {

    searchForm: FormGroup;
    menus = [
        {name: "待发放", code: 0, value: "1"},
        {name: "发放异常", code: 1, value: "2"}
    ];
    selectMenu: number = 0;

    constructor(public route: ActivatedRoute, public router: Router, private supplierWithdrawService: SupplierWithdrawService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, supplierWithdrawService);
        this.buildForm();
    }

    /*
     * 发放
     * */
    grant(id) {
        this.dialogService.confirm('提示', '确认要发放吗？').then((result) => {
            if (result) {
                this.supplierWithdrawService.grant(id).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.getDatas();
                        this.selectId = "0"
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

    /*
    * 驳回
    * */
    reject(id) {
        this.dialogService.confirm('提示', '确认要驳回吗？').then((result) => {
            if (result) {
                this.supplierWithdrawService.reject(id).subscribe(response => {
                    if (response.result == SUCCESS) {


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

    type = "1";

    ngOnInit() {
        this.pageQuery.addOnlyFilter('state', 1, 'eq');
        this.getDatas();
    }

    onItemClick(i) {
        this.type = this.menus[i].value;
        this.selectMenu = i;
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

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            supplierId: [null],
            supplierName: [null],
            amount: [null],
            cardType: [null],
            cardNo: [null],
            payee: [null],
            state: [null],
            errorDescription: [null],
            applyTime: [null],
            dealTime: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        if(this.type=="1"){
            this.pageQuery.addOnlyFilter('state', 1, 'eq');
        }
        if(this.type=="2"){
            this.pageQuery.addOnlyFilter('state', 2, 'eq');
        }
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            supplierName: null,
            payee: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.supplierName != null) {
            pageQuery.addOnlyFilter('supplierName', searchObj.supplierName, 'contains');
        }
        if (searchObj.payee != null) {
            pageQuery.addOnlyFilter('payee', searchObj.payee, 'contains');
        }
        return pageQuery;
    }

}
