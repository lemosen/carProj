import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Recommend} from '../../models/original/recommend.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {CommodityService} from "../../../services/commodity.service";

@Component({
    selector: 'app-form-recommend',
    templateUrl: './form-recommend.component.html',
    styleUrls: ['./form-recommend.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormRecommendComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() recommend: Recommend = new Recommend();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
    select="请选择"
    setParentCategory(event) {
        if(event==""){
            this.select="请选择"
            this.selectCommodityName = "请选择";
        }else{
            this.select=""
            this.commonForm.patchValue({
            commodities: event.map(e => {
               this.select=event.name
                return {id: e.id,commodityName:e.commodityName}
            })
        });
        }
     }

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
        commodities: [],

    };

    constructor(private fb: FormBuilder, private location: Location, public commodityService: CommodityService, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.recommend !== undefined && value.recommend.currentValue !== undefined) {
            this.setBuildFormValue(this.recommend);
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


            commodities: [[]],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ]

        });

    }

    selectCommodityName = '请选择'

    setBuildFormValue(recommend: Recommend) {
        this.commonForm.setValue({
            title:
            recommend.title
            ,
            commodities: recommend.commodities
            ,
            state:
            recommend.state+""
        });
        if (this.commonForm.value.commodities) {
            this.selectCommodityName="";
            this.commonForm.value.commodities.forEach((e, index) => {
                this.select=""
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
        if (this.recommend) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if(this.select == "请选择"){
            this.dialogService.toast('warning', '提示', '推荐商品不能为空！');
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
