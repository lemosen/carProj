


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Resc} from '../../models/original/resc.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-resc',
  templateUrl: './form-resc.component.html',
  styleUrls: ['./form-resc.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormRescComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() resc: Resc =new Resc();

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
        parentId:[
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        code:[
        {
                         name: 'maxlength',
        msg: '最大16位长度',
        }
                    ],
        name:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        url:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大64位长度',
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
        if (value.resc !== undefined && value.resc.currentValue !== undefined) {
            this.setBuildFormValue(this.resc);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            parentId: [
                    ],
            code: [
                    ],
            name: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(32)
                        ])
                    ],
            url: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(64)
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

    setBuildFormValue(resc: Resc) {
        this.commonForm.setValue({
           /* guid:
            resc.guid
                ,
            parentId:
            resc.parentId
                ,
            code:
            resc.code
                ,
            name:
            resc.name
                ,
            url:
            resc.url
                ,
            createTime:
            resc.createTime
                ,
            deleted:
            resc.deleted
                ,
            delTime:
            resc.delTime
                ,*/

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
 if (this.resc) {
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


    setParentResc(event) {

        if(event.id!=0){
              console.log(event);
        }
    }
}
