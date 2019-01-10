import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Resc} from '../../models/original/resc.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {RescService} from "../../../services/resc.service";
import {Role} from "../../models/original/role.model";
import {SUCCESS} from "../../models/constants.model";
import {RoleService} from "../../../services/role.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";

@Component({
  selector: 'app-list-resc',
  templateUrl: './list-resc.component.html',
  styleUrls: ['./list-resc.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListRescComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() role: Role =new Role();

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
        rescs:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
    };

    constructor(private route: ActivatedRoute,private router:Router,private fb: FormBuilder,private location: Location,private rescService: RescService,private roleService: RoleService,private dialogService: DialogsService) {
        this.buildForm();
    }

 /*   ngOnChanges(value) {
        if (value.role !== undefined && value.role.currentValue !== undefined) {
            this.setBuildFormValue(this.role);
        }
    }
*/
    ngOnChanges(value) {
        if (value.role !== undefined && value.role.currentValue !== undefined) {
            this.setBuildFormValue(this.role);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            /*guid: [
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
                    ],*/
            rescs:[[]]
        });
    }

    setBuildFormValue(role: Role) {
        this.commonForm.setValue({
            rescs:
            role.rescs
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
    submitted: boolean = false;
    onSubmit(){
       /* this.commonForm.patchValue({
            rescs: this.id.map(e => {
                return {id:e}
            })
        });*/
        const formValue = this.submitCheck();
      this.route.params.subscribe((params: ParamMap) => {
            formValue.id=params["objId"]
        })


        const searchObj = this.commonForm.value;

        if (this.role) {
         }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
         console.log("commonForm value=" + JSON.stringify(formValue));
         this.onTransmitFormValue.emit({obj: formValue});
         this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                this.submitted = true;
                this.roleService.jurisdiction(formValue).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/role/list']);
                    }else{
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                },error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        }, () => {
            this.submitted = false;
        });
    }

    reset() {

    }

    goBack(){
        this.location.back();
    }


  /*  id:string[]=[]*/
    setParentResc(event) {
        console.log("SaleRegion ----->"+event);
        this.commonForm.patchValue({
            rescs: event.map(e => {
                return {
                    id: e.id,
                    name: e.value
                }
            })
        });
  }


}
