import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Community} from '../../models/original/community.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {MemberService} from "../../../services/member.service";
import {PageQuery} from "../../models/page-query.model";
import {EditorComponent} from "../../components/editor/editor.component";

@Component({
    selector: 'app-form-community',
    templateUrl: './form-community.component.html',
    styleUrls: ['./form-community.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormCommunityComponent implements OnInit, OnChanges {

    commonForm: FormGroup;
    pageQuery:PageQuery=new PageQuery();

    @ViewChild('editor') editor: EditorComponent;

    @Input() community: Community = new Community();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    descriptionContent = "";

    getPro(event){
        this.commonForm.patchValue({
            province:event.province,
            city:event.city,
            district:event.address
        })
        console.log(event)
    }

    formErrors = {
        member: [
            {
                name: 'required',
                msg: '不可为空',
            },
        ],
        province: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        city: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        district: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        address: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
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
        commissionRate: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大5位长度',
            }
        ],
        description: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        imgPath: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大256位长度',
            }
        ],
        receivingAddress: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大128位长度',
            }
        ],
    };

    constructor(public memberService: MemberService, private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.pageQuery.addOnlyFilter('memberType', 0, 'eq');
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.community !== undefined && value.community.currentValue !== undefined) {
            this.setBuildFormValue(this.community);
        }
    }

    ngOnInit() {
    }

    setCommunityMember(event){
        console.log(event);
        this.commonForm.patchValue({
            member: {
                id: event.id,
                username: event.username,
            }
        });
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            member: this.fb.group({
                id: null,
                username: [
                    "请选择", Validators.compose([Validators.required,
                        Validators.maxLength(16)
                    ])
                ],
            }),
            province: [
              null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            city: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            district: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            address: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            commissionRate: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(5)
                ])
            ],
            description: [
                null, Validators.compose([Validators.required
                ])
            ],
            imgPath: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(256)
                ])
            ],
            receivingAddress: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(125)
                ])
            ],
        });
    }

    city=""
    district=""
    province=""
    setBuildFormValue(community: Community) {
        let member: any = community.member
        if (!member) {
            member = {
                id: 0,
                username: "请选择"
            }
        }
        this.commonForm.setValue({
            member: {
                id: member.id,
                username: member.username,
            },
            province:
            community.province
            ,
            city:
            community.city
            ,
            district:
            community.district
            ,
            address:
            community.address
            ,
            state:
            community.state + ""
            ,
            commissionRate:
            community.commissionRate
            ,
            description:
            community.description
            ,
            imgPath:
            community.imgPath
            ,
            receivingAddress:
            community.receivingAddress
            ,
        });
        this.descriptionContent = this.commonForm.value.description;
        //赋值富文本
        this.editor.setContent(community.description);

        if(this.commonForm.value.city!=null){
             this.city= this.commonForm.value.city;
        }
        if(this.commonForm.value.district!=null){
             this.district= this.commonForm.value.district;
        }
        if(this.commonForm.value.province!=null){
            this.province= this.commonForm.value.province;
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
        console.log(formValue)
        if (this.community) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if (this.commonForm.get("member.username").value == "请选择") {
            this.dialogService.toast('warning', '提示', '请选择管理员！');
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

    thematicText(event) {
        this.commonForm.patchValue({
            description: event
        });
    }

}
