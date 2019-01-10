

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {CouponUseService} from '../../../services/coupon-use.service';
import {CouponUse} from '../../models/original/coupon-use.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-coupon-use',
  templateUrl: './edit-coupon-use.component.html',
  styleUrls: ['./edit-coupon-use.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditCouponUseComponent implements OnInit {

  submitted: boolean = false;

  couponUse: CouponUse;

  constructor(private route: ActivatedRoute,private router:Router,private couponUseService: CouponUseService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.couponUseService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.couponUse = response.data;
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
                this.couponUseService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/couponUse/list']);
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
