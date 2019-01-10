


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Product} from '../../models/original/product.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-product',
  templateUrl: './form-product.component.html',
  styleUrls: ['./form-product.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormProductComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() product: Product =new Product();

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
        productNo:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        productName:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大64位长度',
        }
                    ],
        commodityId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        categoryId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
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
        sort:[
        {
                         name: 'maxlength',
        msg: '最大3位长度',
        }
                    ],
        distribution:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        commissionRate:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        freightType:[
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        unifiedFreight:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        freightTemplateId:[
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        stockSet:[
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        volume:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        weight:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        shelf:[
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        description:[
        {
                         name: 'maxlength',
        msg: '最大65535位长度',
        }
                    ],
        attrName:[
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        attrValue:[
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        originalPrice:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        currentPrice:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        memberPrice:[
        {
                         name: 'maxlength',
        msg: '最大15位长度',
        }
                    ],
        sku:[
        {
                         name: 'maxlength',
        msg: '最大64位长度',
        }
                    ],
        stock:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
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
        if (value.product !== undefined && value.product.currentValue !== undefined) {
            this.setBuildFormValue(this.product);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            productNo: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(32)
                        ])
                    ],
            productName: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(64)
                        ])
                    ],
            commodityId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            categoryId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            supplierId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            sort: [
                    ],
            distribution: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
                        ])
                    ],
            commissionRate: [
                    ],
            freightType: [
                    ],
            unifiedFreight: [
                    ],
            freightTemplateId: [
                    ],
            stockSet: [
                    ],
            volume: [
                    ],
            weight: [
                    ],
            shelf: [
                    ],
            description: [
                    ],
            attrName: [
                    ],
            attrValue: [
                    ],
            originalPrice: [
                    ],
            currentPrice: [
                    ],
            memberPrice: [
                    ],
            sku: [
                    ],
            stock: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
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

    setBuildFormValue(product: Product) {
        this.commonForm.setValue({
            guid:
            product.guid
                ,
            productNo:
            product.productNo
                ,
            productName:
            product.productName
                ,
            commodityId:
            product.commodityId
                ,
            categoryId:
            product.categoryId
                ,
            supplierId:
            product.supplierId
                ,
            sort:
            product.sort
                ,
            distribution:
            product.distribution
                ,
            commissionRate:
            product.commissionRate
                ,
            freightType:
            product.freightType
                ,
            unifiedFreight:
            product.unifiedFreight
                ,
            freightTemplateId:
            product.freightTemplateId
                ,
            stockSet:
            product.stockSet
                ,
            volume:
            product.volume
                ,
            weight:
            product.weight
                ,
            shelf:
            product.shelf
                ,
            description:
            product.description
                ,
            attrName:
            product.attrName
                ,
            attrValue:
            product.attrValue
                ,
            originalPrice:
            product.originalPrice
                ,
            currentPrice:
            product.currentPrice
                ,
            memberPrice:
            product.memberPrice
                ,
            sku:
            product.sku
                ,
            stock:
            product.stock
                ,
            createTime:
            product.createTime
                ,
            deleted:
            product.deleted
                ,
            delTime:
            product.delTime
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
 if (this.product) {
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
