

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {AccountRecord} from '../../models/original/account-record.model';
import {AccountRecordService} from '../../../services/account-record.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-account-record',
  templateUrl: './view-account-record.component.html',
  styleUrls: ['./view-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAccountRecordComponent implements OnInit {

    accountRecord: AccountRecord=new AccountRecord;

    constructor(private route: ActivatedRoute,private location: Location,
                private accountRecordService: AccountRecordService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.accountRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.accountRecord = response.data;
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
