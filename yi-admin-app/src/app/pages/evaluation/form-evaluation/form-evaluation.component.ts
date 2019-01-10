import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Evaluation} from '../../models/original/evaluation.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-evaluation',
    templateUrl: './form-evaluation.component.html',
    styleUrls: ['./form-evaluation.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormEvaluationComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() evaluation: Evaluation = new Evaluation();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        evaluationId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        accountId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        commodityId: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        content: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大256位长度',
            }
        ],
        isShow: [
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
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        star: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.evaluation !== undefined && value.evaluation.currentValue !== undefined) {
            this.setBuildFormValue(this.evaluation);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            accountId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            commodityId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            content: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(256)
                ])
            ],
            isShow: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],
            createTime: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(19)
                ])
            ],
            star: [],
        });
    }

    setBuildFormValue(evaluation: Evaluation) {
        this.commonForm.setValue({
            accountId:
            evaluation.accountId
            ,
            commodityId:
            evaluation.commodityId
            ,
            content:
            evaluation.content
            ,
            isShow:
            evaluation.isShow
            ,
            createTime:
            evaluation.createTime
            ,
            star:
            evaluation.star
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
        if (this.evaluation) {
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
