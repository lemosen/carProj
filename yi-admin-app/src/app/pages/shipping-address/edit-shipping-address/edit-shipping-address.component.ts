

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ShippingAddressService} from '../../../services/shipping-address.service';
import {ShippingAddress} from '../../models/original/shipping-address.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-shipping-address',
  templateUrl: './edit-shipping-address.component.html',
  styleUrls: ['./edit-shipping-address.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditShippingAddressComponent implements OnInit {

  submitted: boolean = false;

  shippingAddress: ShippingAddress;

  constructor(private route: ActivatedRoute,private router:Router,private shippingAddressService: ShippingAddressService, private dialogService: DialogsService) { }

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

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }

    submitForm(formValue) {
        if (this.submitted) {
            return;
        }
        this.dialogService.confirm('提示', '确认要提交吗？').then(result => {
            if (result) {
                this.submitted = true;
                this.shippingAddressService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/shippingAddress/list']);
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        });
    }
}
