import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ExpressTemplate} from '../../models/original/express-template.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-express-template',
    templateUrl: './form-express-template.component.html',
    styleUrls: ['./form-express-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormExpressTemplateComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() expressTemplate: ExpressTemplate = new ExpressTemplate();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        templateNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        templateName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        printWidth: [
            {
                name: 'maxlength',
                msg: '最大11位长度',
            }
        ],
        printHigh: [
            {
                name: 'maxlength',
                msg: '最大11位长度',
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
        if (value.expressTemplate !== undefined && value.expressTemplate.currentValue !== undefined) {
            this.setBuildFormValue(this.expressTemplate);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            templateNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            templateName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            printWidth: [],
            printHigh: [],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
        });
    }

    setBuildFormValue(expressTemplate: ExpressTemplate) {
        this.commonForm.setValue({
            templateNo:
            expressTemplate.templateNo
            ,
            templateName:
            expressTemplate.templateName
            ,
            printWidth:
            expressTemplate.printWidth
            ,
            printHigh:
            expressTemplate.printHigh
            ,
            state:
            expressTemplate.state+""
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
        if (this.expressTemplate) {
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
