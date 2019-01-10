

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ReturnOrderService} from '../../../services/return-order.service';
import {ReturnOrder} from '../../models/original/return-order.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-return-order',
  templateUrl: './edit-return-order.component.html',
  styleUrls: ['./edit-return-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditReturnOrderComponent implements OnInit {

  submitted: boolean = false;

  returnOrder: ReturnOrder;

  constructor(private route: ActivatedRoute,private router:Router,private returnOrderService: ReturnOrderService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.returnOrderService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.returnOrder = response.data;
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
                this.returnOrderService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/returnOrder/list']);
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
