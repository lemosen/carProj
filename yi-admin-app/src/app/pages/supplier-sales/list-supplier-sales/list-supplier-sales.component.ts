import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ProvinceCityService} from '../../../services/province-city.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-supplier-sales',
    templateUrl: './list-supplier-sales.component.html',
    styleUrls: ['./list-supplier-sales.component.scss'],
    encapsulation: ViewEncapsulation.None
})

export class ListSupplierSales extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private provinceCityService: ProvinceCityService, public dialogService: DialogsService) {
        super(route, router, dialogService, provinceCityService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
