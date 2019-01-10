import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AppendixTableService} from '../../../services/appendix-table.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-appendix-table',
    templateUrl: './list-appendix-table.component.html',
    styleUrls: ['./list-appendix-table.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAppendixTableComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private appendixTableService: AppendixTableService, public dialogService: DialogsService) {
        super(route, router, dialogService, appendixTableService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
