import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SupplierService} from '../../../services/supplier.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-supplier',
    templateUrl: './list-supplier.component.html',
    styleUrls: ['./list-supplier.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSupplierComponent extends CommonList implements OnInit {

    list = [{
        key: "可用",
        value: true
    }, {
        key: "冻结",
        value: false

    }, {
        key: "全部",
        value: null
    }];

    constructor(public route: ActivatedRoute, public router: Router, private supplierService: SupplierService, public dialogService: DialogsService) {
        super(route, router, dialogService, supplierService);
    }

    ngOnInit() {
        this.getList();
    }
      supplierProfileSum:number[];//供应商总数
    getList(){
        this.supplierService.getList().subscribe(response => {
            this.supplierProfileSum = response.data;

        })
    }

}
