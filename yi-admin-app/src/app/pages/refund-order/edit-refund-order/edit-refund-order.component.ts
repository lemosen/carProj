

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {RefundOrderService} from '../../../services/refund-order.service';
import {RefundOrder} from '../../models/original/refund-order.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-refund-order',
  templateUrl: './edit-refund-order.component.html',
  styleUrls: ['./edit-refund-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditRefundOrderComponent implements OnInit {

  submitted: boolean = false;

  refundOrder: RefundOrder;

  constructor(private route: ActivatedRoute,private router:Router,private refundOrderService: RefundOrderService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.refundOrderService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.refundOrder = response.data;
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
                this.refundOrderService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/refundOrder/list']);
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
