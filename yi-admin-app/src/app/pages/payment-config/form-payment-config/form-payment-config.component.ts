


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PaymentConfig} from '../../models/original/payment-config.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-payment-config',
  templateUrl: './form-payment-config.component.html',
  styleUrls: ['./form-payment-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormPaymentConfigComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() paymentConfig: PaymentConfig =new PaymentConfig();

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
        payType:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        appId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        method:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大128位长度',
        }
                    ],
        charset:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        signType:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        sign:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
        version:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        notifyUrl:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        mchId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        appKey:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        appSecret:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        createTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
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
        if (value.paymentConfig !== undefined && value.paymentConfig.currentValue !== undefined) {
            this.setBuildFormValue(this.paymentConfig);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            payType: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
                        ])
                    ],
            appId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(32)
                        ])
                    ],
            method: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(128)
                        ])
                    ],
            charset: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            signType: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            sign: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(19)
                        ])
                    ],
            version: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            notifyUrl: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            mchId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(32)
                        ])
                    ],
            appKey: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(32)
                        ])
                    ],
            appSecret: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(32)
                        ])
                    ],
            createTime: [
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

    setBuildFormValue(paymentConfig: PaymentConfig) {
        this.commonForm.setValue({
            guid:
            paymentConfig.guid
                ,
            payType:
            paymentConfig.payType
                ,
            appId:
            paymentConfig.appId
                ,
            method:
            paymentConfig.method
                ,
            charset:
            paymentConfig.charset
                ,
            signType:
            paymentConfig.signType
                ,
            sign:
            paymentConfig.sign
                ,
            version:
            paymentConfig.version
                ,
            notifyUrl:
            paymentConfig.notifyUrl
                ,
            mchId:
            paymentConfig.mchId
                ,
            appKey:
            paymentConfig.appKey
                ,
            appSecret:
            paymentConfig.appSecret
                ,
            createTime:
            paymentConfig.createTime
                ,
            deleted:
            paymentConfig.deleted
                ,
            delTime:
            paymentConfig.delTime
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
 if (this.paymentConfig) {
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
