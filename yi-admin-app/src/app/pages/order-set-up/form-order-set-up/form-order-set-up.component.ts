import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Refund} from '../../models/original/refund.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-order-set-up',
    templateUrl: './form-order-set-up.component.html',
    styleUrls: ['./form-order-set-up.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormOrderSetUpComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() refund: Refund = new Refund();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);



    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        /*if (value.refund !== undefined && value.refund.currentValue !== undefined) {
            this.setBuildFormValue(this.refund);
        }*/
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

    reset() {

    }

    goBack() {
        this.location.back();
    }

}
