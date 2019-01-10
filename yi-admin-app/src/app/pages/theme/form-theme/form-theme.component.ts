import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Article} from '../../models/original/article.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {CommodityService} from "../../../services/commodity.service";
import {EditorComponent} from "../../components/editor/editor.component";

@Component({
    selector: 'app-form-theme',
    templateUrl: './form-theme.component.html',
    styleUrls: ['./form-theme.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormThemeComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() article: Article = new Article();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('editor') editor: EditorComponent;

    formErrors = {

        id: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        guid: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
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
        subtitle: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        author: [
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
                name: 'maxlength',
                msg: '最大65535位长度',
            }
        ],
        imgPath: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大255位长度',
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
        state: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
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
        commodities: [
            {
                name: 'maxlength',
                msg: '最大19位长度',
            }
        ],

    };

    constructor(private fb: FormBuilder, private location: Location, public commodityService: CommodityService, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.article !== undefined && value.article.currentValue !== undefined) {
            this.setBuildFormValue(this.article);
        }
    }
   /* commodities:number[]*/
    ngOnInit() {
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            // guid: [
            //         ],
            title: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(127)
                ])
            ],
            subtitle: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(127)
                ])
            ],
            state: [
                0, Validators.compose([Validators.required,
                ])
            ],
            imgPath:[
                null, Validators.compose([Validators.required,
                    Validators.maxLength(127)
                ])
            ],
            commodities: [[]],
        });
        this.selectCommodityName = '请选择'
    }
      recallConten="";
    selectCommodityName = ''
    setBuildFormValue(article: Article) {
        this.commonForm.setValue({
            title:
            article.title
            ,
            subtitle:
            article.subtitle
            ,
            imgPath:
            article.imgPath
            ,
            state:
            article.state
            ,
            commodities:article.commodities
        });

        this.recallConten= article.imgPath
        // alert(this.recallConten);
        this.editor.setContent(article.imgPath)
        if (this.commonForm.value.commodities) {
            this.selectCommodityName=""
            this.commonForm.value.commodities.forEach((e, index) => {
                if (this.commonForm.value.commodities.length != index + 1) {
                    this.selectCommodityName += e.commodityName + ","
                } else {
                    this.selectCommodityName += e.commodityName
                }
            })
        } else {

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
        console.log(formValue);
        if (this.article) {

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


    setParentCategory(event) {
        console.log(event);
        this.commonForm.patchValue({
            commodities: event.map(e => {
                return {id: e.id}
            })
        });
        console.log(this.commonForm.value.commodities);
    }

    thematicText(event) {
        console.log(event);
        this.commonForm.patchValue({
            imgPath: event
        });
    }





}
