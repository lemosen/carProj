

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ShippingAddress} from '../../models/original/shipping-address.model';
import {ShippingAddressService} from '../../../services/shipping-address.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-shipping-address',
  templateUrl: './view-shipping-address.component.html',
  styleUrls: ['./view-shipping-address.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewShippingAddressComponent implements OnInit {

    shippingAddress: ShippingAddress=new ShippingAddress;

    constructor(private route: ActivatedRoute,private location: Location,
                private shippingAddressService: ShippingAddressService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.shippingAddressService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.shippingAddress = response.data;
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
