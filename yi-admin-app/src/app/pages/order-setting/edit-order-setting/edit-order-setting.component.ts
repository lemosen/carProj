

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {OrderSettingService} from '../../../services/order-setting.service';
import {OrderSetting} from '../../models/original/order-setting.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-order-setting',
  templateUrl: './edit-order-setting.component.html',
  styleUrls: ['./edit-order-setting.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditOrderSettingComponent implements OnInit {

  submitted: boolean = false;

  orderSetting: OrderSetting;

  constructor(private route: ActivatedRoute,private router:Router,private orderSettingService: OrderSettingService, private dialogService: DialogsService) { }

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
                formValue.obj.id=this.orderSetting.id;
                this.orderSettingService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/order-setting/list']);
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
