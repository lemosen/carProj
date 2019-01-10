


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ShippingAddress} from '../../models/original/shipping-address.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from
        '@angular/common';

@Component({
  selector: 'app-form-shipping-address',
  templateUrl: './form-shipping-address.component.html',
  styleUrls: ['./form-shipping-address.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormShippingAddressComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() shippingAddress: ShippingAddress =new ShippingAddress();

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
        fullName:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        phone:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        province:[
        {
                         name: 'maxlength',
        msg: '最大8位长度',
        }
                    ],
        city:[
        {
                         name: 'maxlength',
        msg: '最大8位长度',
        }
                    ],
        district:[
        {
                         name: 'maxlength',
        msg: '最大8位长度',
        }
                    ],
        address:[
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        default:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
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
        if (value.shippingAddress !== undefined && value.shippingAddress.currentValue !== undefined) {
            this.setBuildFormValue(this.shippingAddress);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            memberId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            fullName: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(16)
                        ])
                    ],
            phone: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(16)
                        ])
                    ],
            province: [
                    ],
            city: [
                    ],
            district: [
                    ],
            address: [
                    ],
            default: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
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

    setBuildFormValue(shippingAddress: ShippingAddress) {
        this.commonForm.setValue({
            guid:
            shippingAddress.guid
                ,
            memberId:
            shippingAddress.memberId
                ,
            fullName:
            shippingAddress.fullName
                ,
            phone:
            shippingAddress.phone
                ,
            province:
            shippingAddress.province
                ,
            city:
            shippingAddress.city
                ,
            district:
            shippingAddress.district
                ,
            address:
            shippingAddress.address
                ,
            default:
            shippingAddress.default
                ,
            createTime:
            shippingAddress.createTime
                ,
            deleted:
            shippingAddress.deleted
                ,
            delTime:
            shippingAddress.delTime
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
 if (this.shippingAddress) {
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
