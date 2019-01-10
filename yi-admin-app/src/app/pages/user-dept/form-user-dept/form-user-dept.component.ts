import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {UserDept} from '../../models/original/user-dept.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-user-dept',
    templateUrl: './form-user-dept.component.html',
    styleUrls: ['./form-user-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormUserDeptComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() userDept: UserDept = new UserDept();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        userDeptId: [
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
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        deptId: [
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
        if (value.userDept !== undefined && value.userDept.currentValue !== undefined) {
            this.setBuildFormValue(this.userDept);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            userId: [],
            deptId: [],
        });
    }

    setBuildFormValue(userDept: UserDept) {
        this.commonForm.setValue({
            userId:
            userDept.userId
            ,
            deptId:
            userDept.deptId
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
        if (this.userDept) {
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
