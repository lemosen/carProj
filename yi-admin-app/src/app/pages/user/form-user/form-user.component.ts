import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {User} from '../../models/original/user.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {DeptService} from "../../../services/dept.service";

@Component({
    selector: 'app-form-user',
    templateUrl: './form-user.component.html',
    styleUrls: ['./form-user.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormUserComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() user: User = new User();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    imgPathPic="";

    getPic(event) {
        this.imgPathPic=event[0].url;
    }


    formErrors = {

        dept: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        username: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        password: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        fullName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        phone: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        email: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        jobNo: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        avatar: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        state: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
    };

    constructor(public deptService: DeptService, private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    setUserDept(event) {
        console.log(event);
        this.commonForm.patchValue({
            dept: {
                id: event.id,
                deptName: event.deptName,
            }
        });
    }

    ngOnChanges(value) {
        if (value.user !== undefined && value.user.currentValue !== undefined) {
            this.setBuildFormValue(this.user);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            dept: this.fb.group({
                id: null,
                deptName:  [
                    "请选择", Validators.compose([Validators.required
                    ])
                ],
            }),
            username: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            password: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            fullName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            phone: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            email: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            jobNo: [],
            avatar: [],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
        });
    }

    setBuildFormValue(user: User) {
        let dept:any =user.dept
        this.commonForm.setValue({
            dept: {
                id:dept.id,
                deptName:dept.deptName,
            }
            ,
            username:
            user.username
            ,
            password:
            user.password
            ,
            fullName:
            user.fullName
            ,
            phone:
            user.phone
            ,
            email:
            user.email
            ,
            jobNo:
            user.jobNo
            ,
            avatar:
            user.avatar
            ,
            state:
            user.state+""
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

        if(this.imgPathPic!=""){
            this.commonForm.value.avater=this.imgPathPic;
        }
        const formValue = this.submitCheck();

        if (this.user) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if(this.commonForm.value.dept.deptName=="请选择"){
            this.dialogService.toast('warning', '提示', '请选择所属部门！');
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
