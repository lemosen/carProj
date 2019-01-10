import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AppendixTable} from '../../models/original/appendix-table.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-appendix-table',
    templateUrl: './form-appendix-table.component.html',
    styleUrls: ['./form-appendix-table.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAppendixTableComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() appendixTable: AppendixTable = new AppendixTable();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        attachId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        objectType: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        filePath: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        objectId: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        objectPath: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        fileName: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        fileExt: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        fileType: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        fileSize: [
            {
                name: 'maxlength',
                msg: '最大11位长度',
            }
        ],
        fsGuid: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        createTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        userId: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        userName: [
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        description: [
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.appendixTable !== undefined && value.appendixTable.currentValue !== undefined) {
            this.setBuildFormValue(this.appendixTable);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            objectType: [],
            filePath: [],
            objectId: [],
            objectPath: [],
            fileName: [],
            fileExt: [],
            fileType: [],
            fileSize: [],
            fsGuid: [],
            createTime: [],
            userId: [],
            userName: [],
            description: [],
        });
    }

    setBuildFormValue(appendixTable: AppendixTable) {
        this.commonForm.setValue({
            objectType:
            appendixTable.objectType
            ,
            filePath:
            appendixTable.filePath
            ,
            objectId:
            appendixTable.objectId
            ,
            objectPath:
            appendixTable.objectPath
            ,
            fileName:
            appendixTable.fileName
            ,
            fileExt:
            appendixTable.fileExt
            ,
            fileType:
            appendixTable.fileType
            ,
            fileSize:
            appendixTable.fileSize
            ,
            fsGuid:
            appendixTable.fsGuid
            ,
            createTime:
            appendixTable.createTime
            ,
            userId:
            appendixTable.userId
            ,
            userName:
            appendixTable.userName
            ,
            description:
            appendixTable.description
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
        if (this.appendixTable) {
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
