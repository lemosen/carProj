import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AttributeName} from '../../models/original/attribute-name.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {AttachmentService} from "../../../services/attachment.service";
import {AttributeNameService} from "../../../services/attribute-name.service";
import {AttributeGroupService} from "../../../services/attribute-group.service";

@Component({
    selector: 'app-form-attribute-name',
    templateUrl: './form-attribute-name.component.html',
    styleUrls: ['./form-attribute-name.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAttributeNameComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() attributeName: AttributeName = new AttributeName();

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
        attributeGroupId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        attrName: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
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

    constructor(public attributeGroupService: AttributeGroupService, private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    setAttributeGroupGroupName(event) {
        console.log(event);
        this.commonForm.patchValue({
            attributeGroup: {
                id: event.id,
                groupName: event.groupName,
            }
        });
    }

    ngOnChanges(value) {
        if (value.attributeName !== undefined && value.attributeName.currentValue !== undefined) {
            this.setBuildFormValue(this.attributeName);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            attributeGroup: this.fb.group({
                id: null,
                groupName: [null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                    ])
                ]
            }),
            attrName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
        });
    }

    setBuildFormValue(attributeName: AttributeName) {
        let dbAttributeGroup: any = attributeName.attributeGroup;
        this.commonForm.setValue({
            attributeGroup: {
                id: dbAttributeGroup.id,
                groupName: dbAttributeGroup.groupName
            },
            attrName:
            attributeName.attrName
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
        if (this.attributeName) {
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
