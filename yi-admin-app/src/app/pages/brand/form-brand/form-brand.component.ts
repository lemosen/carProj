


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Brand} from '../../models/original/brand.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-brand',
  templateUrl: './form-brand.component.html',
  styleUrls: ['./form-brand.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormBrandComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() brand: Brand =new Brand();

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
        brandNo:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        cnName:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        enName:[
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        imgPath:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大255位长度',
        }
                    ],
        state:[
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
        if (value.brand !== undefined && value.brand.currentValue !== undefined) {
            this.setBuildFormValue(this.brand);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            brandNo: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(16)
                        ])
                    ],
            cnName: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(32)
                        ])
                    ],
            enName: [
                    ],
            imgPath: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(255)
                        ])
                    ],
            state: [
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

    setBuildFormValue(brand: Brand) {
        this.commonForm.setValue({
            brandNo:
            brand.brandNo
                ,
            cnName:
            brand.cnName
                ,
            enName:
            brand.enName
                ,
            imgPath:
            brand.imgPath
                ,
            state:
            brand.state
                ,
            createTime:
            brand.createTime
                ,
            deleted:
            brand.deleted
                ,
            delTime:
            brand.delTime
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
 if (this.brand) {
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
