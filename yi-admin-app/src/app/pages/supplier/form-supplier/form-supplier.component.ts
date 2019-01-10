import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Supplier} from '../../models/original/supplier.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {UserService} from "../../../services/user.service";

@Component({
    selector: 'app-form-supplier',
    templateUrl: './form-supplier.component.html',
    styleUrls: ['./form-supplier.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormSupplierComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() supplier: Supplier = new Supplier();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    getPro(event){
        this.commonForm.patchValue({
            province:event.province,
            city:event.city,
            district:event.address
        })
        console.log(event)
    }



    formErrors = {

        user: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        supplierNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        supplierName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        phone: [
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
                msg: '最大64位长度',
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
        contact: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        contactPhone: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        province: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        city: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        district: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大8位长度',
            }
        ],
        address: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        remark: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],

    };

    constructor(public userService: UserService, private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    setSupplierUser(event) {
        console.log(event);
        this.commonForm.patchValue({
            user: {
                id: event.id,
                username: event.username,
            }
        });
    }

    ngOnChanges(value) {
        if (value.supplier !== undefined && value.supplier.currentValue !== undefined) {
            this.setBuildFormValue(this.supplier);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            user: this.fb.group({
                id: null,
                username: [
                    "请选择", Validators.compose([Validators.required,
                        Validators.maxLength(16)
                    ])
                ],
            }),
            supplierNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            supplierName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            phone: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            password: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            contact: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            contactPhone: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            province: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(8)
                ])
            ],
            city: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(8)
                ])
            ],
            district: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(8)
                ])
            ],
            address: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(8)
                ])
            ],
            remark: [],
        });
    }
    city=""
    district=""
    province=""
    setBuildFormValue(supplier: Supplier) {
        let user:any = supplier.user
        this.commonForm.setValue({
            user: {
                id: user.id,
                username: user.username,
            }
            ,
            supplierNo:
            supplier.supplierNo
            ,
            supplierName:
            supplier.supplierName
            ,
            phone:
            supplier.phone
            ,
            password:
            supplier.password
            ,
            state:
            supplier.state + ""
            ,
            contact:
            supplier.contact
            ,
            contactPhone:
            supplier.contactPhone
            ,
            province:
            supplier.province
            ,
            city:
            supplier.city
            ,
            district:
            supplier.district
            ,
            address:
            supplier.address
            ,
            remark:
            supplier.remark
            ,
        });
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
        if (this.supplier) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if (this.commonForm.value.user.username=="请选择") {
            this.dialogService.toast('warning', '提示', '请选择用户！');
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
