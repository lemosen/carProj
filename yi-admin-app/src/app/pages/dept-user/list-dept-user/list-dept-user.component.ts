import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {DeptUserService} from '../../../services/dept-user.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-dept-user',
    templateUrl: './list-dept-user.component.html',
    styleUrls: ['./list-dept-user.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListDeptUserComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private deptUserService: DeptUserService, public dialogService: DialogsService) {
        super(route, router, dialogService, deptUserService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
