import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {OrderSetting} from '../../models/original/order-setting.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-order-setting',
    templateUrl: './form-order-setting.component.html',
    styleUrls: ['./form-order-setting.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormOrderSettingComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() orderSetting: OrderSetting = new OrderSetting();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    setTypes = [{
        code: 1,
        title: "待付款",
    }, {
        code: 2,
        title: "待评价",
    }]

    formErrors = {

        setType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        timeout: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        day: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        hour: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        minute: [
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
        if (value.orderSetting !== undefined && value.orderSetting.currentValue !== undefined) {
            this.setBuildFormValue(this.orderSetting);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            setType: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            timeout: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            day: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            hour: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            minute: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
        });
    }

    setBuildFormValue(orderSetting: OrderSetting) {
        this.commonForm.setValue({
            setType:
            orderSetting.setType
            ,
            timeout:
            orderSetting.timeout
            ,
            day:
            orderSetting.day
            ,
            hour:
            orderSetting.hour
            ,
            minute:
            orderSetting.minute
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
        if (this.orderSetting) {
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
