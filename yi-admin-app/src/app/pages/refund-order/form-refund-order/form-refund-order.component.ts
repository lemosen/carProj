import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RefundOrder} from '../../models/original/refund-order.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-refund-order',
    templateUrl: './form-refund-order.component.html',
    styleUrls: ['./form-refund-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormRefundOrderComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() refundOrder: RefundOrder = new RefundOrder();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        id: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        guid: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        refundNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        state: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        memberId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        memberPhone: [
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        orderId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        orderNo: [
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        orderAmount: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        refundAmount: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        refundMode: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        refundType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        refundReason: [
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        remark: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        deleted: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        delTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.refundOrder !== undefined && value.refundOrder.currentValue !== undefined) {
            this.setBuildFormValue(this.refundOrder);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            id: [],
            refundNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            memberId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            memberPhone: [],
            orderId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            orderNo: [],
            orderAmount: [],
            refundAmount: [],
            refundMode: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            refundType: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            refundReason: [],
            remark: [],
        });
    }

    setBuildFormValue(refundOrder: RefundOrder) {
        this.commonForm.setValue({
            id:
            refundOrder.id
            ,
            refundNo:
            refundOrder.refundNo
            ,
            state:
            refundOrder.state
            ,
            memberId:
            refundOrder.memberId
            ,
            memberPhone:
            refundOrder.memberPhone
            ,
            orderId:
            refundOrder.orderId
            ,
            orderNo:
            refundOrder.orderNo
            ,
            orderAmount:
            refundOrder.orderAmount
            ,
            refundAmount:
            refundOrder.refundAmount
            ,
            refundMode:
            refundOrder.refundMode
            ,
            refundType:
            refundOrder.refundType
            ,
            refundReason:
            refundOrder.refundReason
            ,
            remark:
            refundOrder.remark
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
        if (this.refundOrder) {
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
