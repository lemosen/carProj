

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {CouponService} from '../../../services/coupon.service';
import {Coupon} from '../../models/original/coupon.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-coupon',
  templateUrl: './edit-coupon.component.html',
  styleUrls: ['./edit-coupon.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditCouponComponent implements OnInit {

  submitted: boolean = false;

  coupon: Coupon;

  constructor(private route: ActivatedRoute,private router:Router,private couponService: CouponService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.couponService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.coupon = response.data;
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

                formValue.obj.id=this.coupon.id
                console.log(formValue.obj)
                this.submitted = true;
                this.couponService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/coupon/list']);
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
