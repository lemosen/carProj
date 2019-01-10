


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Process} from '../../models/original/process.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-process',
  templateUrl: './form-process.component.html',
  styleUrls: ['./form-process.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormProcessComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() process: Process =new Process();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);



    formErrors = {

        id:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        guid:[
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        refundOrderId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        returnOrderId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        userId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        username:[
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        processType:[
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        processTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
        remark:[
        {
                         name: 'maxlength',
        msg: '最大127位长度',
        }
                    ],
        deleted:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        delTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
    };

    constructor(private fb: FormBuilder,private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.process !== undefined && value.process.currentValue !== undefined) {
            this.setBuildFormValue(this.process);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            refundOrderId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            returnOrderId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            userId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            username: [
                    ],
            processType: [
                    ],
            processTime: [
                    ],
            remark: [
                    ],
            deleted: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
                        ])
                    ],
            delTime: [
                    ],
        });
    }

    setBuildFormValue(process: Process) {
        this.commonForm.setValue({
            guid:
            process.guid
                ,
            refundOrderId:
            process.refundOrderId
                ,
            returnOrderId:
            process.returnOrderId
                ,
            userId:
            process.userId
                ,
            username:
            process.username
                ,
            processType:
            process.processType
                ,
            processTime:
            process.processTime
                ,
            remark:
            process.remark
                ,
            deleted:
            process.deleted
                ,
            delTime:
            process.delTime
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
 if (this.process) {
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

    goBack(){
        this.location.back();
    }

}
