

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ReturnReason} from '../../models/original/return-reason.model';
import {ReturnReasonService} from '../../../services/return-reason.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-return-reason',
  templateUrl: './view-return-reason.component.html',
  styleUrls: ['./view-return-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewReturnReasonComponent implements OnInit {

    returnReason: ReturnReason=new ReturnReason;

    constructor(private route: ActivatedRoute,private location: Location,
                private returnReasonService: ReturnReasonService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.returnReasonService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.returnReason = response.data;
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
