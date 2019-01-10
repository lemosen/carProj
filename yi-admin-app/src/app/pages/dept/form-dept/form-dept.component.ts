import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Dept} from '../../models/original/dept.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {DeptService} from "../../../services/dept.service";
import {PageQuery} from "../../models/page-query.model";

@Component({
    selector: 'app-form-dept',
    templateUrl: './form-dept.component.html',
    styleUrls: ['./form-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormDeptComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() dept: Dept = new Dept();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        id: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        parent: [
            {
                name: '',
                msg: '',
            }
        ],
        deptNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        deptName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        description: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
    };

    constructor(public deptService: DeptService, private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.dept !== undefined && value.dept.currentValue !== undefined) {
            this.setBuildFormValue(this.dept);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            parent: this.fb.group({
                id: null,
                deptName: "请选择",
            }),
            deptNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            deptName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            description: [],
        });
    }

    setBuildFormValue(dept: Dept) {
        let parent: any = dept.parent;
        if (!parent) {
            parent = {
                id: 0,
                deptName: "请选择"
            }
        }
        this.commonForm.setValue({
            parent: {
                id: parent.id,
                deptName: parent.deptName,
            },
            deptNo:
            dept.deptNo
            ,
            deptName:
            dept.deptName
            ,
            description:
            dept.description
            ,
        });
    }

    setParentDept(event) {
        console.log(event);
        this.commonForm.patchValue({
            parent: {
                id: event.id,
                deptName: event.deptName,
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
        if (this.dept) {
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
