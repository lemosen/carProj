import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AccountCouponService} from '../../../services/account-coupon.service';
import {AccountCoupon} from '../../models/original/account-coupon.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-account-coupon',
    templateUrl: './edit-account-coupon.component.html',
    styleUrls: ['./edit-account-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditAccountCouponComponent implements OnInit {

    submitted: boolean = false;

    accountCoupon: AccountCoupon;

    constructor(private route: ActivatedRoute, private router: Router, private accountCouponService: AccountCouponService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.accountCouponService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.accountCoupon = response.data;
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
                this.accountCouponService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
