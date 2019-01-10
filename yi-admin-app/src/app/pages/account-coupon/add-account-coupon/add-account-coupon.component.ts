import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {AccountCoupon} from '../../models/original/account-coupon.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AccountCouponService} from '../../../services/account-coupon.service';

@Component({
    selector: 'app-add-account-coupon',
    templateUrl: './add-account-coupon.component.html',
    styleUrls: ['./add-account-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAccountCouponComponent implements OnInit {

    submitted: boolean = false;

    accountCoupon: AccountCoupon;

    constructor(private router: Router, private accountCouponService: AccountCouponService, private dialogService: DialogsService) {
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
                this.accountCouponService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/accountCoupon/list']);
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
