import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ArticleCommdity} from '../../models/original/article-commdity.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-article-commdity',
    templateUrl: './form-article-commdity.component.html',
    styleUrls: ['./form-article-commdity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormArticleCommdityComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() articleCommdity: ArticleCommdity = new ArticleCommdity();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        aricleId: [
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
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.articleCommdity !== undefined && value.articleCommdity.currentValue !== undefined) {
            this.setBuildFormValue(this.articleCommdity);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            aricleId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            commodityId: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
        });
    }

    setBuildFormValue(articleCommdity: ArticleCommdity) {
        this.commonForm.setValue({
            aricleId:
            articleCommdity.aricleId
            ,
            commodityId:
            articleCommdity.commodityId
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
        if (this.articleCommdity) {
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
