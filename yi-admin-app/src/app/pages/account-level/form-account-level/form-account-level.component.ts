import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountLevel} from '../../models/original/account-level.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-account-level',
    templateUrl: './form-account-level.component.html',
    styleUrls: ['./form-account-level.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAccountLevelComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() accountLevel: AccountLevel = new AccountLevel();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        levelId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        levelName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        orderNum: [
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

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.accountLevel !== undefined && value.accountLevel.currentValue !== undefined) {
            this.setBuildFormValue(this.accountLevel);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            levelName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            orderNum: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
        });
    }

    setBuildFormValue(accountLevel: AccountLevel) {
        this.commonForm.setValue({
            levelName:
            accountLevel.levelName
            ,
            orderNum:
            accountLevel.orderNum
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
        if (this.accountLevel) {
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
