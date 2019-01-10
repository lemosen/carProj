import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PrizeBo} from '../../../models/domain/bo/prize-bo.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {PageQuery} from "../../../models/page-query.model";
import {CommodityService} from "../../../services/commodity.service";
import {CouponService} from "../../../services/coupon.service";

@Component({
  selector: 'form-prize',
  templateUrl: './form-prize.component.html',
  styleUrls: ['./form-prize.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormPrizeComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() prize: PrizeBo = new PrizeBo();
  commodityPageQuery: PageQuery = new PageQuery();
  couponPageQuery: PageQuery = new PageQuery();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    name: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    prizeType: [
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    integral: [
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    commodity: [
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    coupon: [
      {
        name: 'maxlength',
        msg: '最大10位长度',
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
    remark: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService,public commodityService: CommodityService,public couponService:CouponService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.prize !== undefined && value.prize.currentValue !== undefined) {
      this.setBuildFormValue(this.prize);
    }
  }

  ngOnInit() {
    this.commodityPageQuery.addFilter("state", 2, "eq");
    this.commodityPageQuery.addFilter("shelf", 1, "eq");
    this.couponPageQuery.addFilter("couponType", 1, "eq");
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      name: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      prizeType: [1, Validators.compose([Validators.required])],
      integral: [0],
      commodity: [],
      coupon: [],
      state: [0, Validators.compose([Validators.required, Validators.maxLength(3)])],
      remark: [null, Validators.compose([Validators.maxLength(127)])],
    });
  }

  setBuildFormValue(prize: PrizeBo) {
    this.commonForm.setValue({
      name:
      prize.name
      ,
      prizeType:
      prize.prizeType
      ,
      integral:
      prize.integral
      ,
      commodity:
      prize.commodity
      ,
      coupon:
      prize.coupon
      ,
      state:
      prize.state
      ,
      remark:
      prize.remark
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


  onSubmit() {
    const formValue = this.submitCheck();
    if (this.prize) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
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

  setCommodity(event) {
    if(event.commodityName != null && event.length !=0){
      this.commonForm.patchValue({
        commodity: {
          id: event.id,
          commodityName: event.commodityName,
        }
      });
    }
  }

  setCoupon(event) {
    if(event.couponName != null && event.length !=0){
      this.commonForm.patchValue({
        coupon: {
          id: event.id,
          couponName: event.couponName,
        }
      });
    }
  }

}
