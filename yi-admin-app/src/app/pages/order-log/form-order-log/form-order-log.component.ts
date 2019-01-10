


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {OrderLog} from '../../models/original/order-log.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-order-log',
  templateUrl: './form-order-log.component.html',
  styleUrls: ['./form-order-log.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormOrderLogComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() orderLog: OrderLog =new OrderLog();

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
        msg: '最大32位长度',
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
        state:[
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        operateTime:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
        operator:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        remark:[
        {
                         name: 'maxlength',
        msg: '最大127位长度',
        }
                    ],
    };

    constructor(private fb: FormBuilder,private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.orderLog !== undefined && value.orderLog.currentValue !== undefined) {
            this.setBuildFormValue(this.orderLog);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            orderId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            orderNo: [
                    ],
            state: [
                    ],
            operateTime: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(19)
                        ])
                    ],
            operator: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(32)
                        ])
                    ],
            remark: [
                    ],
        });
    }

    setBuildFormValue(orderLog: OrderLog) {
        this.commonForm.setValue({
            guid:
            orderLog.guid
                ,
            orderId:
            orderLog.orderId
                ,
            orderNo:
            orderLog.orderNo
                ,
            state:
            orderLog.state
                ,
            operateTime:
            orderLog.operateTime
                ,
            operator:
            orderLog.operator
                ,
            remark:
            orderLog.remark
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
 if (this.orderLog) {
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
