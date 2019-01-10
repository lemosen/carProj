import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AccountLevelService} from '../../../services/account-level.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";
import {AccountLevel} from "../../models/original/account-level.model";

@Component({
    selector: 'app-list-account-level',
    templateUrl: './list-account-level.component.html',
    styleUrls: ['./list-account-level.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAccountLevelComponent extends CommonList implements OnInit {



    grade: Array<AccountLevel>;
    constructor(public route: ActivatedRoute, public router: Router, private accountLevelService: AccountLevelService, public dialogService: DialogsService) {
        super(route, router, dialogService, accountLevelService);
    }

    ngOnInit() {
        this.goGrade();
    }
    goGrade(){
        this.accountLevelService.getAllAcountLevel().subscribe(response=>{
            this.grade=response;
            this.getDatas();
        })
    }
    selectValue(id){
        this.pageQuery.addFilter('levelId',id,'eq');
    }


}
