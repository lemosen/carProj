import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FreightTemplate} from '../../models/original/freight-template.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-freight-template',
    templateUrl: './form-freight-template.component.html',
    styleUrls: ['./form-freight-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormFreightTemplateComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() freightTemplate: FreightTemplate = new FreightTemplate();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    getPro(event){
        this.commonForm.patchValue({
            province:event.province,
            city:event.city,
            district:event.address
        })
        console.log(event);
    }

    formErrors = {

        templateNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        templateName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        chargeMode: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
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
        presetWeight: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        presetFee: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        extraWeight: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        extraFee: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        province: [
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        city: [
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        district: [
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        address: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.freightTemplate !== undefined && value.freightTemplate.currentValue !== undefined) {
            this.setBuildFormValue(this.freightTemplate);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            templateNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            templateName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            chargeMode: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            presetWeight: [],
            presetFee: [],
            extraWeight: [],
            extraFee: [],
            province: [],
            city: [],
            district: [],
            address: [],
        });
    }


    city=""
    district=""
    province=""

    setBuildFormValue(freightTemplate: FreightTemplate) {
        this.commonForm.setValue({
            templateNo:
            freightTemplate.templateNo
            ,
            templateName:
            freightTemplate.templateName
            ,
            chargeMode:
            freightTemplate.chargeMode+""
            ,
            state:
            freightTemplate.state+""
            ,
            presetWeight:
            freightTemplate.presetWeight
            ,
            presetFee:
            freightTemplate.presetFee
            ,
            extraWeight:
            freightTemplate.extraWeight
            ,
            extraFee:
            freightTemplate.extraFee
            ,
            province:
            freightTemplate.province
            ,
            city:
            freightTemplate.city
            ,
            district:
            freightTemplate.district
            ,
            address:
            freightTemplate.address
            ,
        });
        if(this.commonForm.value.city!=null){
            this.city= this.commonForm.value.city;
        }
        if(this.commonForm.value.district!=null){
            this.district= this.commonForm.value.district;
        }
        if(this.commonForm.value.province!=null){
            this.province= this.commonForm.value.province;
        }


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
        if (this.freightTemplate) {
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
