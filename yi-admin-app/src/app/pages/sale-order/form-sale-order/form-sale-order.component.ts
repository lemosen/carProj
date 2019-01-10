import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {SaleOrder} from "../../models/original/sale-order.model";


@Component({
    selector: 'app-form-sale-order',
    templateUrl: './form-sale-order.component.html',
    styleUrls: ['./form-sale-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormSaleOrderComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() saleOrder: SaleOrder = new SaleOrder();

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
        orderNo: [
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
        buyerMessage: [
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        consignee: [
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        consigneePhone: [
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        consigneeAddr: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        deliveryMode: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        logisticsCompany: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        trackingNo: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        orderAmount: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        discountAmount: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        freight: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        payAmount: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        tradeNo: [
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        payMode: [
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        createTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        paymentTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        deliveryTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        dealTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
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
        if (value.saleOrder !== undefined && value.saleOrder.currentValue !== undefined) {
            this.setBuildFormValue(this.saleOrder);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [],
            memberId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            orderNo: [],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            buyerMessage: [],
            consignee: [],
            consigneePhone: [],
            consigneeAddr: [],
            deliveryMode: [],
            logisticsCompany: [],
            trackingNo: [],
            orderAmount: [],
            discountAmount: [],
            freight: [],
            payAmount: [],
            tradeNo: [],
            payMode: [],
            createTime: [],
            paymentTime: [],
            deliveryTime: [],
            dealTime: [],
            remark: [],
            deleted: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            delTime: [],
        });
    }

    setBuildFormValue(saleOrder: SaleOrder) {
        this.commonForm.setValue({
            guid:
            saleOrder.guid
            ,
            memberId:
            saleOrder.memberId
            ,
            orderNo:
            saleOrder.orderNo
            ,
            state:
            saleOrder.state
            ,
            buyerMessage:
            saleOrder.buyerMessage
            ,
            consignee:
            saleOrder.consignee
            ,
            consigneePhone:
            saleOrder.consigneePhone
            ,
            consigneeAddr:
            saleOrder.consigneeAddr
            ,
            deliveryMode:
            saleOrder.deliveryMode
            ,
            logisticsCompany:
            saleOrder.logisticsCompany
            ,
            trackingNo:
            saleOrder.trackingNo
            ,
            orderAmount:
            saleOrder.orderAmount
            ,
            discountAmount:
            saleOrder.discountAmount
            ,
            freight:
            saleOrder.freight
            ,
            payAmount:
            saleOrder.payAmount
            ,
            tradeNo:
            saleOrder.tradeNo
            ,
            payMode:
            saleOrder.payMode
            ,
            createTime:
            saleOrder.createTime
            ,
            paymentTime:
            saleOrder.paymentTime
            ,
            deliveryTime:
            saleOrder.deliveryTime
            ,
            dealTime:
            saleOrder.dealTime
            ,
            remark:
            saleOrder.remark
            ,
            deleted:
            saleOrder.deleted
            ,
            delTime:
            saleOrder.delTime
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
        if (this.saleOrder) {
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
