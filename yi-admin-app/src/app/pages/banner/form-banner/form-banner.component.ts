import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Banner} from '../../models/original/banner.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-banner',
    templateUrl: './form-banner.component.html',
    styleUrls: ['./form-banner.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormBannerComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() banner: Banner = new Banner();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('fileUploader') fileUploader;

    formErrors = {

        title: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        content: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大65535位长度',
            }
        ],
        imgPath: [
            {
                name: '',
                msg: '',
            }
        ],
        url: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        sort: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        state: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    imgPathPic="";

    getPic(event) {
        this.imgPathPic=event[0].url;
    }

    ngOnChanges(value) {
        if (value.banner !== undefined && value.banner.currentValue !== undefined) {
            this.setBuildFormValue(this.banner);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            title: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(127)
                ])
            ],
            content: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(255)
                ])],
            imgPath: [],
            url: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(255)
                ])
            ],
            sort: [
                null, Validators.compose([
                    Validators.maxLength(255)
                ])
            ],
            state: [
                null, Validators.compose([Validators.required
                ])
            ],
        });
    }

    setBuildFormValue(banner: Banner) {
        this.commonForm.setValue({
            title:
            banner.title
            ,
            content:
            banner.content
            ,
            imgPath:
            banner.imgPath
            ,
            url:
            banner.url
            ,
            sort:
            banner.sort
            ,
            state:
            banner.state + ""
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
        if(this.imgPathPic!=""){
            formValue.imgPath=this.imgPathPic;
        }
        if (this.banner) {
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
