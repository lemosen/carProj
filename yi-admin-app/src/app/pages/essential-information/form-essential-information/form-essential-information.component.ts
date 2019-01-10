import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Withdraw} from '../../models/original/withdraw.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-essential-information',
    templateUrl: './form-essential-information.component.html',
    styleUrls: ['./form-essential-information.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormEssentialInformationComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() withdraw: Withdraw = new Withdraw();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        withdrawId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        cardNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        isWithdraw: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大1位长度',
            }
        ],
        createTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        name: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        price: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大11位长度',
            }
        ],
        auditTime: [
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
        if (value.withdraw !== undefined && value.withdraw.currentValue !== undefined) {
            this.setBuildFormValue(this.withdraw);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            cardNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            isWithdraw: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            createTime: [],
            name: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            price: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(11)
                ])
            ],
            auditTime: [],
        });
    }

    setBuildFormValue(withdraw: Withdraw) {
        this.commonForm.setValue({
            cardNo:
            withdraw.cardNo
            ,
            isWithdraw:
            withdraw.isWithdraw
            ,
            createTime:
            withdraw.createTime
            ,
            name:
            withdraw.name
            ,
            price:
            withdraw.price
            ,
            auditTime:
            withdraw.auditTime
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
        if (this.withdraw) {
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
