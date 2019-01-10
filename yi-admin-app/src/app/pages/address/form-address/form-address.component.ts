import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Address} from '../../models/original/address.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-address',
    templateUrl: './form-address.component.html',
    styleUrls: ['./form-address.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAddressComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() address: Address = new Address();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        addressId: [
            {
                name: 'required',
                msg: '不可为空',
            },
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
        isDel: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大1位长度',
            }
        ],
        address: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大128位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.address !== undefined && value.address.currentValue !== undefined) {
            this.setBuildFormValue(this.address);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            accountId: [],
            isDel: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            address: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(128)
                ])
            ],
        });
    }

    setBuildFormValue(address: Address) {
        this.commonForm.setValue({
            accountId:
            address.accountId
            ,
            isDel:
            address.isDel
            ,
            address:
            address.address
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
        if (this.address) {
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
