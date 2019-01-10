


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Account} from '../../models/original/account.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-account',
    templateUrl: './form-account.component.html',
    styleUrls: ['./form-account.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAccountComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() account: Account = new Account();

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
        memberId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        orderQuantity: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        consumeAmount: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        balance: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        freezeAmount: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        integral: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        residualIntegral: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        remark: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.account !== undefined && value.account.currentValue !== undefined) {
            this.setBuildFormValue(this.account);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
            ],
            memberId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            orderQuantity: [
            ],
            consumeAmount: [
            ],
            balance: [
            ],
            freezeAmount: [
            ],
            integral: [
            ],
            residualIntegral: [
            ],
            remark: [
            ],
        });
    }

    setBuildFormValue(account: Account) {
        this.commonForm.setValue({
            guid:
            account.guid
            ,
            memberId:
            account.memberId
            ,
            orderQuantity:
            account.orderQuantity
            ,
            consumeAmount:
            account.consumeAmount
            ,
            balance:
            account.balance
            ,
            freezeAmount:
            account.freezeAmount
            ,
            integral:
            account.integral
            ,
            residualIntegral:
            account.residualIntegral
            ,
            remark:
            account.remark
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
        if (this.account) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        console.log("commonForm value=" + JSON.stringify(formValue));
        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

}
