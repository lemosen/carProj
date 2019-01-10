import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ProvinceCity} from '../../models/original/province-city.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-province-city',
    templateUrl: './form-province-city.component.html',
    styleUrls: ['./form-province-city.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormProvinceCityComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() provinceCity: ProvinceCity = new ProvinceCity();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        provinceCityId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        provinceName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        cityName: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.provinceCity !== undefined && value.provinceCity.currentValue !== undefined) {
            this.setBuildFormValue(this.provinceCity);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            provinceName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            cityName: [],
        });
    }

    setBuildFormValue(provinceCity: ProvinceCity) {
        this.commonForm.setValue({
            provinceName:
            provinceCity.provinceName
            ,
            cityName:
            provinceCity.cityName
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
        if (this.provinceCity) {
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
