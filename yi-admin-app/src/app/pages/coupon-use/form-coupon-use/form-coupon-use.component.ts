


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CouponUse} from '../../models/original/coupon-use.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-coupon-use',
  templateUrl: './form-coupon-use.component.html',
  styleUrls: ['./form-coupon-use.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormCouponUseComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() couponUse: CouponUse =new CouponUse();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);



    formErrors = {

        id:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        guid:[
        {
                         name: 'maxlength',
        msg: '最大64位长度',
        }
                    ],
        couponId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        couponNo:[
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        couponReceiveId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        parValue:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        use:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        surplus:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        memberId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        memberPhone:[
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        useTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
        orderId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        orderNo:[
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        deleted:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        delTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
    };

    constructor(private fb: FormBuilder,private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.couponUse !== undefined && value.couponUse.currentValue !== undefined) {
            this.setBuildFormValue(this.couponUse);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            couponId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            couponNo: [
                    ],
            couponReceiveId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            parValue: [
                    ],
            use: [
                    ],
            surplus: [
                    ],
            memberId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            memberPhone: [
                    ],
            useTime: [
                    ],
            orderId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            orderNo: [
                    ],
            deleted: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
                        ])
                    ],
            delTime: [
                    ],
        });
    }

    setBuildFormValue(couponUse: CouponUse) {
        this.commonForm.setValue({
            guid:
            couponUse.guid
                ,
            couponId:
            couponUse.couponId
                ,
            couponNo:
            couponUse.couponNo
                ,
            couponReceiveId:
            couponUse.couponReceiveId
                ,
            parValue:
            couponUse.parValue
                ,
            use:
            couponUse.use
                ,
            surplus:
            couponUse.surplus
                ,
            memberId:
            couponUse.memberId
                ,
            memberPhone:
            couponUse.memberPhone
                ,
            useTime:
            couponUse.useTime
                ,
            orderId:
            couponUse.orderId
                ,
            orderNo:
            couponUse.orderNo
                ,
            deleted:
            couponUse.deleted
                ,
            delTime:
            couponUse.delTime
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
 if (this.couponUse) {
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

    goBack(){
        this.location.back();
    }

}
