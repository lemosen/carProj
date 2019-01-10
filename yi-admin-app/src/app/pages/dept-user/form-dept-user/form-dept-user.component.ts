import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DeptUser} from '../../models/original/dept-user.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-dept-user',
    templateUrl: './form-dept-user.component.html',
    styleUrls: ['./form-dept-user.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormDeptUserComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() deptUser: DeptUser = new DeptUser();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        deptId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        userId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        manager: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大1位长度',
            }
        ],
        showOrder: [
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
        if (value.deptUser !== undefined && value.deptUser.currentValue !== undefined) {
            this.setBuildFormValue(this.deptUser);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            deptId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            userId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            manager: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            showOrder: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
        });
    }

    setBuildFormValue(deptUser: DeptUser) {
        this.commonForm.setValue({
            deptId:
            deptUser.deptId
            ,
            userId:
            deptUser.userId
            ,
            manager:
            deptUser.manager
            ,
            showOrder:
            deptUser.showOrder
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
        if (this.deptUser) {
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
