


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RecommendProduct} from '../../models/original/recommend-product.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-recommend-product',
  templateUrl: './form-recommend-product.component.html',
  styleUrls: ['./form-recommend-product.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormRecommendProductComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() recommendProduct: RecommendProduct =new RecommendProduct();

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
        recommendId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
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
        productCode:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大64位长度',
        }
                    ],
        productName:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大255位长度',
        }
                    ],
        imgPath:[
        {
                         name: 'required',
        msg: '不可为空',
        },
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
        if (value.recommendProduct !== undefined && value.recommendProduct.currentValue !== undefined) {
            this.setBuildFormValue(this.recommendProduct);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            recommendId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            productId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            productCode: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(64)
                        ])
                    ],
            productName: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(255)
                        ])
                    ],
            imgPath: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(127)
                        ])
                    ],
        });
    }

    setBuildFormValue(recommendProduct: RecommendProduct) {
        this.commonForm.setValue({
            guid:
            recommendProduct.guid
                ,
            recommendId:
            recommendProduct.recommendId
                ,
            productId:
            recommendProduct.productId
                ,
            productCode:
            recommendProduct.productCode
                ,
            productName:
            recommendProduct.productName
                ,
            imgPath:
            recommendProduct.imgPath
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
 if (this.recommendProduct) {
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
