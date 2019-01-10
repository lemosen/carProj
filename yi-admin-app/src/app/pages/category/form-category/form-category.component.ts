import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Category} from '../../models/original/category.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {CategoryService} from "../../../services/category.service";

@Component({
    selector: 'app-form-category',
    templateUrl: './form-category.component.html',
    styleUrls: ['./form-category.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormCategoryComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() category: Category = new Category();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('fileUploader') fileUploader;

    @ViewChild('tree') tree

    categoryChoose="请选择"

    formErrors = {

        parent: [
            {
                name: '',
                msg: '',
            }
        ],
        categoryNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        categoryName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        imgPath: [
            {
                name: '',
                msg: '',
            }
        ],
        sort: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        remark: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
    };

    constructor(public categoryService: CategoryService, private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    imgPathPic="";

    getPic(event) {
        this.imgPathPic=event[0].url;
    }

    ngOnChanges(value) {
        if (value.category !== undefined && value.category.currentValue !== undefined) {
            this.setBuildFormValue(this.category);
        }
    }

    ngOnInit() {

    }

    setParentCategory(event) {
        // this.categoryChoose=event.name;
        this.commonForm.patchValue({
            parent: {
                id: event.id,
                categoryName: event.name,
            }
        });

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            parent: this.fb.group({
                id: null,
                categoryName: "请选择",
            }),
            categoryNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(16)
                ])
            ],
            categoryName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            imgPath: [],
            sort: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            remark: [null],
        });
    }

    setBuildFormValue(category: Category) {
        let parent: any = category.parent;
        this.commonForm.setValue({
            parent: {
                id:parent.id,
                categoryName: parent.categoryName,
            }
            ,
            categoryNo:
            category.categoryNo
            ,
            categoryName:
            category.categoryName
            ,
            imgPath:
            category.imgPath
            ,
            sort:
            category.sort
            ,
            remark:
            category.remark
            ,
        });
        this.categoryChoose=parent.categoryName;
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
        if (this.category) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if(this.commonForm.value.sort>127){
            this.dialogService.toast('warning', '提示', '排序最大值为127！');
            return;
        }
//        console.log("commonForm value=" + JSON.stringify(formValue));
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

    open(){
        this.tree.open();
    }

}
