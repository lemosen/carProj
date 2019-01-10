import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {LogisticsService} from '../../../services/logistics.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-logistics',
    templateUrl: './list-logistics.component.html',
    styleUrls: ['./list-logistics.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListLogisticsComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private logisticsService: LogisticsService, public dialogService: DialogsService) {
        super(route, router, dialogService, logisticsService);
    }

    ngOnInit() {
        /*this.getDatas();*/
    }
}
