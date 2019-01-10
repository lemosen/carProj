import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReturnReason} from '../../models/original/return-reason.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-return-reason',
    templateUrl: './form-return-reason.component.html',
    styleUrls: ['./form-return-reason.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormReturnReasonComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() returnReason: ReturnReason = new ReturnReason();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        reasonType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        sort: [
            {
                name: 'required',
                msg: '不可为空',
            },
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
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.returnReason !== undefined && value.returnReason.currentValue !== undefined) {
            this.setBuildFormValue(this.returnReason);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            reasonType: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(127)
                ])
            ],
            sort: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
        });
    }

    setBuildFormValue(returnReason: ReturnReason) {
        this.commonForm.setValue({
            reasonType:
            returnReason.reasonType
            ,
            sort:
            returnReason.sort
            ,
            state:
            returnReason.state+""
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

      console.log()
            /* s*/

        const formValue = this.submitCheck();
        if(typeof formValue.sort=="number"){

        }
        if (this.returnReason) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if(this.commonForm.value.sort>127){
            this.dialogService.toast('warning', '提示', '排序最大值为127！');
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
