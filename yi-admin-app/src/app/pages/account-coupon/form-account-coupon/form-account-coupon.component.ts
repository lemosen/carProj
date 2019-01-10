import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountCoupon} from '../../models/original/account-coupon.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-account-coupon',
    templateUrl: './form-account-coupon.component.html',
    styleUrls: ['./form-account-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAccountCouponComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() accountCoupon: AccountCoupon = new AccountCoupon();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        couponId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        accountId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.accountCoupon !== undefined && value.accountCoupon.currentValue !== undefined) {
            this.setBuildFormValue(this.accountCoupon);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            couponId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            accountId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
        });
    }

    setBuildFormValue(accountCoupon: AccountCoupon) {
        this.commonForm.setValue({
            couponId:
            accountCoupon.couponId
            ,
            accountId:
            accountCoupon.accountId
            ,
        });
    }

    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
        if (commonFormValid) {
            return this.commonForm.value;
        }
        return null;
    }

    onSubmit() {
        const formValue = this.submitCheck();
        if (this.accountCoupon) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        console.log("commonForm value=" + JSON.stringify(formValue));
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

}
