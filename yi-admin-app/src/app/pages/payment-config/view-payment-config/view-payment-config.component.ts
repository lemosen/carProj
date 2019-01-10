

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {PaymentConfig} from '../../models/original/payment-config.model';
import {PaymentConfigService} from '../../../services/payment-config.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-payment-config',
  templateUrl: './view-payment-config.component.html',
  styleUrls: ['./view-payment-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewPaymentConfigComponent implements OnInit {

    paymentConfig: PaymentConfig=new PaymentConfig;

    constructor(private route: ActivatedRoute,private location: Location,
                private paymentConfigService: PaymentConfigService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.paymentConfigService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.paymentConfig = response.data;
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
