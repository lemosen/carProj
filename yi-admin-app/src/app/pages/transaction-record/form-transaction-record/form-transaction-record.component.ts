import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TransactionRecord} from '../../models/original/transaction-record.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-transaction-record',
    templateUrl: './form-transaction-record.component.html',
    styleUrls: ['./form-transaction-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormTransactionRecordComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() transactionRecord: TransactionRecord = new TransactionRecord();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        recordId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        recordPrice: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大11位长度',
            }
        ],
        recordType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        recordMode: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        creatTime: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        remark: [
            {
                name: 'maxlength',
                msg: '最大256位长度',
            }
        ],
        no: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.transactionRecord !== undefined && value.transactionRecord.currentValue !== undefined) {
            this.setBuildFormValue(this.transactionRecord);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            recordPrice: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(11)
                ])
            ],
            recordType: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            recordMode: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            creatTime: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(19)
                ])
            ],
            remark: [],
            no: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
        });
    }

    setBuildFormValue(transactionRecord: TransactionRecord) {
        this.commonForm.setValue({
            recordPrice:
            transactionRecord.recordPrice
            ,
            recordType:
            transactionRecord.recordType
            ,
            recordMode:
            transactionRecord.recordMode
            ,
            creatTime:
            transactionRecord.creatTime
            ,
            remark:
            transactionRecord.remark
            ,
            no:
            transactionRecord.no
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
        if (this.transactionRecord) {
            formValue.recordId = this.transactionRecord.recordId;
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
