import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Member} from '../../models/original/member.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {MemberLevelService} from "../../../services/member-level.service";

@Component({
    selector: 'app-form-member',
    templateUrl: './form-member.component.html',
    styleUrls: ['./form-member.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormMemberComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() member: Member = new Member();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {
        parentId: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        username: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        password: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        nickname: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        memberLevel: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        memberType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        province: [
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        city: [
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        district: [
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        address: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        createTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
        deleted: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
        delTime: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, public memberLevelService: MemberLevelService, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.member !== undefined && value.member.currentValue !== undefined) {
            this.setBuildFormValue(this.member);
        }
    }

    ngOnInit() {

    }
    getPro(event){
        this.commonForm.patchValue({
            province:event.province,
            city:event.city,
            district:event.address
        })
        console.log(event);
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
           /* parentId: [],*/
        /*    username: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],*/
           /* password: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],*/
           /* nickname: [],*/
            memberLevel: {
                id: null,
                name: "请选择"
            },
          /*  memberType: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(1)
                ])
            ],*/
            province: [],
            city: [],
            district: [],
            address: [],
           /* createTime: [],
            deleted: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(0)
                ])
            ],
            delTime: [],*/
        });
    }


    setMemberLevel(event) {
        console.log(event);
        this.commonForm.patchValue({
            memberLevel: {
                id: event.id,
                name: event.name
            }
        });
    }
    province="";
    city="";
    district="";
    setBuildFormValue(member: Member) {
        this.commonForm.setValue({
            // id:member.id
            // ,
            // username:
            // member.username
            // ,
        /*    password:
            member.password
            ,*/
          /*  nickname:
            member.nickname
            ,*/
            memberLevel: {
                id: member.memberLevel.id,
                name: member.memberLevel.name
            }
            ,
         /*   memberType:
            member.memberType
            ,*/
            province:
            member.province
            ,
            city:
            member.city
            ,
            district:
            member.district
            ,
            address:
            member.address
            ,
         /*   createTime:
            member.createTime
            ,
            deleted:
            member.deleted
            ,
            delTime:
            member.delTime
            ,*/
        });
        if(this.commonForm.value.province!=null) {
            this.province = this.commonForm.value.province;
        }
        if(this.commonForm.value.city!=null) {
            this.city= this.commonForm.value.city;
        }
        if(this.commonForm.value.district!=null) {
            this.district= this.commonForm.value.district;
        }



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
        if (this.member) {
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
