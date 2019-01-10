import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SystemLogService} from '../../../services/system-log.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-system-log',
    templateUrl: './list-system-log.component.html',
    styleUrls: ['./list-system-log.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSystemLogComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private systemLogService: SystemLogService, public dialogService: DialogsService) {
        super(route, router, dialogService, systemLogService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
