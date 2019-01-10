

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {PaymentConfigService} from '../../../services/payment-config.service';
import {PaymentConfig} from '../../models/original/payment-config.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-payment-config',
  templateUrl: './edit-payment-config.component.html',
  styleUrls: ['./edit-payment-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditPaymentConfigComponent implements OnInit {

  submitted: boolean = false;

  paymentConfig: PaymentConfig;

  constructor(private route: ActivatedRoute,private router:Router,private paymentConfigService: PaymentConfigService, private dialogService: DialogsService) { }

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
                this.paymentConfigService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/paymentConfig/list']);
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
