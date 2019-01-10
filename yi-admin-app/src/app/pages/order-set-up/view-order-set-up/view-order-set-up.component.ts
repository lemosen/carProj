

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {SaleOrder} from '../../models/original/sale-order.model';
import {SaleOrderService} from '../../../services/sale-order.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-order-set-up',
  templateUrl: './view-order-set-up.component.html',
  styleUrls: ['./view-order-set-up.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewOrderSetUpComponent implements OnInit {

    saleOrder: SaleOrder=new SaleOrder;

    constructor(private route: ActivatedRoute,private location: Location,
                private saleOrderService: SaleOrderService, private dialogService: DialogsService) { }


    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.saleOrderService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.saleOrder = response.data;
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