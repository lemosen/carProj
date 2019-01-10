import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Refund} from '../../models/original/refund.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-express-single-template',
    templateUrl: './form-express-single-template.component.html',
    styleUrls: ['./form-express-single-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormExpressSingleTemplateComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() refund: Refund = new Refund();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);



    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.refund !== undefined && value.refund.currentValue !== undefined) {
            this.setBuildFormValue(this.refund);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            orderId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            isRefund: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(5)
                ])
            ],
            remark: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(256)
                ])
            ],
            createTime: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(19)
                ])
            ],
            auditTime: [],
            logisticsNo: [],
            isReturn: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(5)
                ])
            ],
        });
    }

    setBuildFormValue(refund: Refund) {
        this.commonForm.setValue({
            orderId:
            refund.orderId
            ,
            isRefund:
            refund.isRefund
            ,
            remark:
            refund.remark
            ,
            createTime:
            refund.createTime
            ,
            auditTime:
            refund.auditTime
            ,
            logisticsNo:
            refund.logisticsNo
            ,
            isReturn:
            refund.isReturn
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
        if (this.refund) {
            formValue.orderId = this.refund.orderId
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
