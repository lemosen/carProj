
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {CouponUse} from '../../models/original/coupon-use.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {CouponUseService} from '../../../services/coupon-use.service';

@Component({
    selector: 'app-add-coupon-use',
    templateUrl: './add-coupon-use.component.html',
    styleUrls: ['./add-coupon-use.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddCouponUseComponent implements OnInit {

    submitted: boolean = false;

    couponUse: CouponUse;

    constructor(private router:Router,private couponUseService: CouponUseService, private dialogService: DialogsService) {
    }

    ngOnInit() {
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
        this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                this.submitted = true;
                this.couponUseService.add(formValue.obj).subscribe(response => {
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
        }, () => {
            this.submitted = false;
        });
    }
}
