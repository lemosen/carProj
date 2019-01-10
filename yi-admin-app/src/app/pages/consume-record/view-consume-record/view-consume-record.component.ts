

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ConsumeRecord} from '../../models/original/consume-record.model';
import {ConsumeRecordService} from '../../../services/consume-record.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-consume-record',
  templateUrl: './view-consume-record.component.html',
  styleUrls: ['./view-consume-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewConsumeRecordComponent implements OnInit {

    consumeRecord: ConsumeRecord=new ConsumeRecord;

    constructor(private route: ActivatedRoute,private location: Location,
                private consumeRecordService: ConsumeRecordService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.consumeRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.consumeRecord = response.data;
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
