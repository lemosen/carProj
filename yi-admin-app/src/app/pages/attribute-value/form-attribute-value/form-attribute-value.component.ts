


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AttributeValue} from '../../models/original/attribute-value.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {AttributeNameService} from "../../../services/attribute-name.service";

@Component({
  selector: 'app-form-attribute-value',
  templateUrl: './form-attribute-value.component.html',
  styleUrls: ['./form-attribute-value.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormAttributeValueComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() attributeValue: AttributeValue =new AttributeValue();

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
        attrValue:[
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        state:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大3位长度',
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
        attrNameId:[
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

    constructor(public attributeNameService:AttributeNameService,private fb: FormBuilder,private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.attributeValue !== undefined && value.attributeValue.currentValue !== undefined) {
            this.setBuildFormValue(this.attributeValue);
        }
    }

    ngOnInit() {

    }

    setAttributeNameAttrName(event) {
        console.log(event);
        this.commonForm.patchValue({
            attributeName: {
                id: event.id,
                attrName: event.attrName,
            }
        });
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            attrValue: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
                    ],
            attributeName: this.fb.group({
                id: null,
                attrName: [null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                    ])
                ]
            }),
        });
    }

    setBuildFormValue(attributeValue: AttributeValue) {
        this.commonForm.setValue({
            attrValue:
            attributeValue.attrValue
                ,
            attributeName:{
                id:attributeValue.attributeName.id,
                attrName:attributeValue.attributeName.attrName
            },
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
 if (this.attributeValue) {
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
