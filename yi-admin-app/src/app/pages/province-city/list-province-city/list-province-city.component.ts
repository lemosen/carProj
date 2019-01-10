import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ProvinceCityService} from '../../../services/province-city.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-province-city',
    templateUrl: './list-province-city.component.html',
    styleUrls: ['./list-province-city.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListProvinceCityComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private provinceCityService: ProvinceCityService, public dialogService: DialogsService) {
        super(route, router, dialogService, provinceCityService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
