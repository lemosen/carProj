import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SupplierService} from '../../../services/supplier.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";
import {PlatformDataModule} from "../platform-data.module";
import {PlatformDataModel} from "../../models/original/platform-data.model";

@Component({
    selector: 'app-list-platform-data',
    templateUrl: './list-platform-data.component.html',
    styleUrls: ['./list-platform-data.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListPlatformDataComponent extends CommonList implements OnInit {

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
        this.getDatas();
         this.getPlatformdata();

    }

    youFunction(value) {
        this.pageQuery.addOnlyFilter('isUse', value, 'eq')
    }
    platformdata:PlatformDataModel
    getPlatformdata(){

            this.supplierService.getPlatformdata().subscribe(response => {
                this.platformdata = response.data;

            })
    }

}
