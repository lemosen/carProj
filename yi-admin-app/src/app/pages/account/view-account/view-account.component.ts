

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Account} from '../../models/original/account.model';
import {AccountService} from '../../../services/account.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-account',
  templateUrl: './view-account.component.html',
  styleUrls: ['./view-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAccountComponent implements OnInit {

    account: Account=new Account;

    constructor(private route: ActivatedRoute,private location: Location,
                private accountService: AccountService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.accountService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.account = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    goBack(){
        this.location.back();
    }

}
