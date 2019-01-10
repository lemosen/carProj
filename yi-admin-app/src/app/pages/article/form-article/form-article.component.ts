import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Article} from '../../models/original/article.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {CommodityService} from "../../../services/commodity.service";
import {EditorComponent} from "../../components/editor/editor.component";

@Component({
    selector: 'app-form-article',
    templateUrl: './form-article.component.html',
    styleUrls: ['./form-article.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormArticleComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() article: Article = new Article();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('editor') editor: EditorComponent;

    @ViewChild('fileUploader') fileUploader;


    formErrors = {

        title: [
            {
                name: 'required',
                msg: '请输入标题',
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
        imgPath:[
            {
                name: '',
                msg: '',
            }
        ],
        content: [
            {
                name: 'required',
                msg: '不可为空',
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

    imgPathPic="";

    getPic(event) {
        this.imgPathPic=event[0].url;
    }
    authorPic=""
    getAuthor(event) {
        this.authorPic=event[0].url;
    }

    ngOnChanges(value) {
        if (value.article !== undefined && value.article.currentValue !== undefined) {
            this.setBuildFormValue(this.article);
        }
    }

    /* commodities:number[]*/
    ngOnInit() {
    }

    recallContent = ""

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
            content: [
                null, Validators.compose([Validators.required
                ])
            ],
            imgPath:[],
            state: [
                0, Validators.compose([Validators.required,
                ])
            ],
            commodities: [[]],
        });
        this.selectCommodityName = "请选择"
    }

    selectCommodityName = ''

    setBuildFormValue(article: Article) {
        this.commonForm.setValue({
            title:
            article.title
            ,
            subtitle:
            article.subtitle
            ,
            content:
            article.content
            ,
            author:
            article.author
            ,
            imgPath:
            article.imgPath,
            state:
            article.state
            ,
            commodities:
            article.commodities
        });
        this.recallContent = article.content;
        //赋值富文本
        this.editor.setContent(article.content)
        if (this.commonForm.value.commodities) {
            this.selectCommodityName=""
            this.commonForm.value.commodities.forEach((e, index) => {
                if (this.commonForm.value.commodities.length != index + 1) {
                    this.selectCommodityName += e.commodityName + ","
                } else {
                    this.selectCommodityName += e.commodityName
                }
            })
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
        if(this.imgPathPic!=""){
            formValue.imgPath=this.imgPathPic;
        }
        if(this.authorPic!=""){
            formValue.author=this.authorPic;
        }
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
                return {id: e.id,commodityName:e.commodityName}
            })
        });
        console.log(this.commonForm.value.commodities);
    }

    thematicText(event) {
        this.commonForm.patchValue({
            content: event
        });
    }

}
