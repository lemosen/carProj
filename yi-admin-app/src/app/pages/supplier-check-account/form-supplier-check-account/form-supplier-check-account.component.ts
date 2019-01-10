


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SupplierCheckAccount} from '../../models/original/supplier-check-account.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-supplier-check-account',
  templateUrl: './form-supplier-check-account.component.html',
  styleUrls: ['./form-supplier-check-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormSupplierCheckAccountComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() supplierCheckAccount: SupplierCheckAccount =new SupplierCheckAccount();

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
        supplierId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        supplierName:[
        {
                         name: 'maxlength',
        msg: '最大127位长度',
        }
                    ],
        saleOrderId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        saleOrderNo:[
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        orderTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
        productId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        productNo:[
        {
                         name: 'maxlength',
        msg: '最大127位长度',
        }
                    ],
        productName:[
        {
                         name: 'maxlength',
        msg: '最大255位长度',
        }
                    ],
        supplyPrice:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        quantity:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大11位长度',
        }
                    ],
        settlementAmount:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        settlementTime:[
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
        if (value.supplierCheckAccount !== undefined && value.supplierCheckAccount.currentValue !== undefined) {
            this.setBuildFormValue(this.supplierCheckAccount);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            supplierId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            supplierName: [
                    ],
            saleOrderId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            saleOrderNo: [
                    ],
            orderTime: [
                    ],
            productId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            productNo: [
                    ],
            productName: [
                    ],
            supplyPrice: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(15)
                        ])
                    ],
            quantity: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(11)
                        ])
                    ],
            settlementAmount: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(15)
                        ])
                    ],
            settlementTime: [
                    ],
        });
    }

    setBuildFormValue(supplierCheckAccount: SupplierCheckAccount) {
        this.commonForm.setValue({
            guid:
            supplierCheckAccount.guid
                ,
            supplierId:
            supplierCheckAccount.supplierId
                ,
            supplierName:
            supplierCheckAccount.supplierName
                ,
            saleOrderId:
            supplierCheckAccount.saleOrderId
                ,
            saleOrderNo:
            supplierCheckAccount.saleOrderNo
                ,
            orderTime:
            supplierCheckAccount.orderTime
                ,
            productId:
            supplierCheckAccount.productId
                ,
            productNo:
            supplierCheckAccount.productNo
                ,
            productName:
            supplierCheckAccount.productName
                ,
            supplyPrice:
            supplierCheckAccount.supplyPrice
                ,
            quantity:
            supplierCheckAccount.quantity
                ,
            settlementAmount:
            supplierCheckAccount.settlementAmount
                ,
            settlementTime:
            supplierCheckAccount.settlementTime
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
 if (this.supplierCheckAccount) {
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
