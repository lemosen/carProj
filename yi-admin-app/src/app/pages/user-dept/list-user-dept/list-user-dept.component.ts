import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UserDeptService} from '../../../services/user-dept.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-user-dept',
    templateUrl: './list-user-dept.component.html',
    styleUrls: ['./list-user-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListUserDeptComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private userDeptService: UserDeptService, public dialogService: DialogsService) {
        super(route, router, dialogService, userDeptService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
