


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Advertisement} from '../../models/original/advertisement.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-advertisement',
  templateUrl: './form-advertisement.component.html',
  styleUrls: ['./form-advertisement.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormAdvertisementComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() advertisement: Advertisement =new Advertisement();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);



    formErrors = {

        id:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        guid:[
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        title:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大127位长度',
        }
                    ],
        imgPath:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大127位长度',
        }
                    ],
        url:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大255位长度',
        }
                    ],
        state:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        createTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
        deleted:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        delTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
    };

    constructor(private fb: FormBuilder,private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.advertisement !== undefined && value.advertisement.currentValue !== undefined) {
            this.setBuildFormValue(this.advertisement);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            title: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(127)
                        ])
                    ],
            imgPath: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(127)
                        ])
                    ],
            url: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(255)
                        ])
                    ],
            state: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
                        ])
                    ],
            createTime: [
                    ],
            deleted: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
                        ])
                    ],
            delTime: [
                    ],
        });
    }

    setBuildFormValue(advertisement: Advertisement) {
        this.commonForm.setValue({
            guid:
            advertisement.guid
                ,
            title:
            advertisement.title
                ,
            imgPath:
            advertisement.imgPath
                ,
            url:
            advertisement.url
                ,
            state:
            advertisement.state
                ,
            createTime:
            advertisement.createTime
                ,
            deleted:
            advertisement.deleted
                ,
            delTime:
            advertisement.delTime
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
 if (this.advertisement) {
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

    goBack(){
        this.location.back();
    }

}
