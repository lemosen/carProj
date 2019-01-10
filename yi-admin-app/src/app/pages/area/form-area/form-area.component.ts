import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Area} from '../../models/original/area.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-area',
    templateUrl: './form-area.component.html',
    styleUrls: ['./form-area.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAreaComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() area: Area = new Area();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        areaId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        provinceCityId: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        accountId: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        addressId: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        areaName: [
            {
                name: 'required',
                msg: '不可为空',
            },
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
        if (value.area !== undefined && value.area.currentValue !== undefined) {
            this.setBuildFormValue(this.area);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            provinceCityId: [],
            accountId: [],
            addressId: [],
            areaName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
        });
    }

    setBuildFormValue(area: Area) {
        this.commonForm.setValue({
            provinceCityId:
            area.provinceCityId
            ,
            accountId:
            area.accountId
            ,
            addressId:
            area.addressId
            ,
            areaName:
            area.areaName
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
        if (this.area) {
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
