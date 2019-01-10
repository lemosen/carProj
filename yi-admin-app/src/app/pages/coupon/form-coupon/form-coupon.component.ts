import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Coupon} from '../../models/original/coupon.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {MemberLevelService} from "../../../services/member-level.service";
import {CommodityService} from "../../../services/commodity.service";


@Component({
    selector: 'app-form-coupon',
    templateUrl: './form-coupon.component.html',
    styleUrls: ['./form-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormCouponComponent implements OnInit, OnChanges {

    commonForm: FormGroup;


    @Input() coupon: Coupon = new Coupon();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    /* threshold: boolean = true*/
    @ViewChild("dateTime1") dateTime1;
    @ViewChild("dateTime2") dateTime2;

    selectValue(type, value) {
        this.commonForm.get(type).setValue(value);
    }

    /*   if (value == 3) {
       this.threshold = false
   } else {
       this.threshold = true
   }*/

    limiteds = "请选择"
    voucherType = "请选择"
    selectCommodityName = '请选择'


    listAmount = [
        {
            key: "不限量",
            value: 0
        }, {
            key: "一张",
            value: 1
        }, {
            key: "二张",
            value: 2
        }, {
            key: "五张",
            value: 5
        }, {
            key: "十张",
            value: 10
        }, {
            key: "五十张",
            value: 50
        }, {
            key: "一百张",
            value: 100
        }];

    couponType = [{
        key: "满减券",
        value: 1
    }, {
        key: "买送券",
        value: 2
    }];
    /*
    , {
        key: "储值券",
        value: 3
    }
    */

    formErrors = {

        couponNo: [
            {
                name: '',
                msg: '',
            }
        ],
        couponName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        couponType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        parValue: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        quantity: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        useCondition: [
            {
                name: '',
                msg: '',
            }
        ],
        receiveMode: [
            {
                name: 'required',
                msg: '不能为空',
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
        limited: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        validType: [
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        startTime: [
            {
                name: '',
                msg: '',
            }
        ],
        endTime: [
            {
                name: '',
                msg: '',
            }
        ],
        fixedDay: [
            {
                name: '',
                msg: '',
            }
        ],
        commodities: [
            {
                name: '',
                msg: '',
            }
        ],
        display: [
            {
                name: '',
                msg: '',
            }
        ],
        memberLevelName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
    };
    choice = true

    chose() {
        this.commonForm.patchValue({
            commodities: !this.commonForm.value.commodities
        })
        this.choice = !this.choice;
        if (!this.choice) {
            /*   this.commonForm.patchValue({
                   products:null
               });*/
        }
    }

    selection: boolean = false;

    //不限制
    unrestricted() {
        this.selection = false;
        this.commonForm.patchValue({
            useCondition: 0
        });
        /*this.commonForm.patchValue({
            useCondition:""
        });*/
    }

    //限制
    limit() {
        this.selection = true;
    }

    faceValue: number;

    fullSubtraction(e) {
        this.faceValue = e
        console.log(e)
        this.commonForm.patchValue({
            useCondition: e
        });
        // this.reduce=e
    }

    constructor(private fb: FormBuilder, public commodityService: CommodityService, private location: Location, public memberLevelService: MemberLevelService, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.coupon !== undefined && value.coupon.currentValue !== undefined) {
            this.setBuildFormValue(this.coupon);
        }
    }


    startTime(event){
        console.log(event.year + "-" + event.month + "-" + event.day);
        this.commonForm.patchValue({
            startTime: event.year + "-" + event.month + "-" + event.day+" 00:00:00"
        });
    }

    endTime(event){
        console.log(event.year + "-" + event.month + "-" + event.day);
        this.commonForm.patchValue({
            endTime: event.year + "-" + event.month + "-" + event.day+" 00:00:00"
        });
    }


    ngOnInit() {

    }

    setMemberLevel(event) {
        this.commonForm.patchValue({
            memberLevel: {
                id: event.id,
                name: event.name
            },
            memberLevelName: event.name
        });
    }


    setParentCategory(event) {
        console.log(event)
        this.commonForm.patchValue({
            commodities: event.map(e => {
                return {id: e.id, commodityName: e.commodityName}
            })
        });
    }


    buildForm(): void {
        this.commonForm = this.fb.group({
            couponNo: [],
            couponName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            couponType: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            parValue: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
            quantity: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            useCondition: [],
            receiveMode: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            memberLevel: {
                id: [
                    null, Validators.compose([Validators.required,
                        Validators.maxLength(10)
                    ])
                ],
                name: "请选择"
            },
            limited: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            validType: [],
            startTime: [],
            endTime: [],
            fixedDay: [],
            commodities: [[]],
            display: [],
            memberLevelName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(15)
                ])
            ],
        });
        this.selectCommodityName = '请选择'

    }

    /*  aaa="bbb"*/
    startTimes: string[] = ["", "", ""];
    endTimes: string[] = ["", "", ""];

    setBuildFormValue(coupon: Coupon) {
        this.commonForm.setValue({
            couponNo:
            coupon.couponNo
            ,
            couponName:
            coupon.couponName
            ,
            couponType:
            coupon.couponType
            ,
            parValue:
            coupon.parValue
            ,
            quantity:
            coupon.quantity
            ,
            /*  useQuantity:
              coupon.useQuantity
                  ,*/
            useCondition:
            coupon.useCondition + ""
            ,
            receiveMode:
            coupon.receiveMode + ""
            ,
            memberLevel: {
                id: coupon.memberLevel.id,
                name: coupon.memberLevel.name
            }
            ,
            limited:
            coupon.limited
            ,
            validType:
            coupon.validType + ""
            ,
            startTime:
            coupon.startTime
            ,
            endTime:
            coupon.endTime
            ,
            fixedDay:
            coupon.fixedDay
            ,
            commodities: coupon.commodities
            ,
            display: coupon.useCondition
            ,
            memberLevelName: coupon.memberLevel.name
        });
        /*if(this.commonForm.value.validType==1){
            this.commonForm.setValue({
                fixedDay:null
            })
        }*/
        if (coupon.startTime != null) {
            this.startTimes = coupon.startTime.split("-");
            this.dateTime1.setModelPopup({
                year: parseInt(this.startTimes[0]),
                month: parseInt(this.startTimes[1]),
                day: parseInt(this.startTimes[2])
            })
        }
        if (coupon.endTime != null) {
            this.endTimes = coupon.endTime.split("-");
            this.dateTime2.setModelPopup({
                year: parseInt(this.endTimes[0]),
                month: parseInt(this.endTimes[1]),
                day: parseInt(this.endTimes[2])
            })
        }
        if (this.commonForm.value.useCondition > 0) {
            this.selection = true
            this.commonForm.value.display = this.commonForm.value.useCondition;
        }

        if (this.commonForm.value.couponType) {
            this.voucherType = this.couponType[this.commonForm.value.couponType - 1].key
        }

        if (this.commonForm.value.limited) {
            this.listAmount.forEach(e => {
                if (e.value == this.commonForm.value.limited) {
                    this.limiteds = e.key
                }
            })
        }
        if (this.commonForm.value.commodities.length > 0) {
            this.selectCommodityName = ""
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
        if (this.coupon) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if (this.commonForm.value.useCondition < 0) {
            this.dialogService.toast('warning', '提示', '使用门槛不能为负数！');
            return;
        }
        if (!(this.commonForm.value.useCondition >= 0)) {
            this.dialogService.toast('warning', '提示', '请选择使用门槛！');
            return;
        }
        if (!(this.commonForm.value.validType == 1 || this.commonForm.value.validType == 2)) {
            this.dialogService.toast('warning', '提示', '请选择优惠券有效期！');
            return;
        }
        if(this.commonForm.value.validType==1){
            console.log(this.commonForm.value.startTime)
            this.commonForm.value.fixedDay="";
            console.log(this.commonForm.value.endTime)
        }else if(this.commonForm.value.validType==2){
            this.commonForm.value.startTime="";
            this.commonForm.value.endTime="";
        }

        console.log(this.commonForm.value)
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

}
