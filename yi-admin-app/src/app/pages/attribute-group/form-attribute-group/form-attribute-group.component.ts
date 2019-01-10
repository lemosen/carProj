import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AttributeGroup} from '../../models/original/attribute-group.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-attribute-group',
    templateUrl: './form-attribute-group.component.html',
    styleUrls: ['./form-attribute-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAttributeGroupComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() attributeGroup: AttributeGroup = new AttributeGroup();

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
        guid: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        groupNo: [
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        groupName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        imgPath: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        sort: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
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
        createTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        remark: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        deleted: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        delTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.attributeGroup !== undefined && value.attributeGroup.currentValue !== undefined) {
            this.setBuildFormValue(this.attributeGroup);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            groupNo: [],
            groupName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            imgPath: [],
            remark: [],
        });
    }

    setBuildFormValue(attributeGroup: AttributeGroup) {
        this.commonForm.setValue({
            groupNo:
            attributeGroup.groupNo
            ,
            groupName:
            attributeGroup.groupName
            ,
            imgPath:
            attributeGroup.imgPath
            ,
            remark:
            attributeGroup.remark
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
        if (this.attributeGroup) {
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
