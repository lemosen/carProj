

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {OrderSetting} from '../../models/original/order-setting.model';
import {OrderSettingService} from '../../../services/order-setting.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-order-setting',
  templateUrl: './view-order-setting.component.html',
  styleUrls: ['./view-order-setting.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewOrderSettingComponent implements OnInit {

    orderSetting: OrderSetting=new OrderSetting;

    constructor(private route: ActivatedRoute,private location: Location,
                private orderSettingService: OrderSettingService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.orderSettingService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.orderSetting = response.data;
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
