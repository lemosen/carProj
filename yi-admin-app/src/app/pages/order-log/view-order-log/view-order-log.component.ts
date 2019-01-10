

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {OrderLog} from '../../models/original/order-log.model';
import {OrderLogService} from '../../../services/order-log.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-order-log',
  templateUrl: './view-order-log.component.html',
  styleUrls: ['./view-order-log.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewOrderLogComponent implements OnInit {

    orderLog: OrderLog=new OrderLog;

    constructor(private route: ActivatedRoute,private location: Location,
                private orderLogService: OrderLogService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.orderLogService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.orderLog = response.data;
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
