import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SupplierSaleStat} from '../../../models/original/supplier-sale-stat.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-supplier-sale-stat',
  templateUrl: './form-supplier-sale-stat.component.html',
  styleUrls: ['./form-supplier-sale-stat.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormSupplierSaleStatComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() supplierSaleStat: SupplierSaleStat = new SupplierSaleStat();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

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
    supplierOrderNum: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    saleAmount: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    platformRatio: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    cost: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    profit: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    statTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.supplierSaleStat !== undefined && value.supplierSaleStat.currentValue !== undefined) {
      this.setBuildFormValue(this.supplierSaleStat);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      supplierOrderNum: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      saleAmount: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(15)
        ])
      ],
      platformRatio: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(15)
        ])
      ],
      cost: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(15)
        ])
      ],
      profit: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(15)
        ])
      ],
      statTime: [],
    });
  }

  setBuildFormValue(supplierSaleStat: SupplierSaleStat) {
    this.commonForm.setValue({
      guid:
      supplierSaleStat.guid
      ,
      supplierOrderNum:
      supplierSaleStat.supplierOrderNum
      ,
      saleAmount:
      supplierSaleStat.saleAmount
      ,
      platformRatio:
      supplierSaleStat.platformRatio
      ,
      cost:
      supplierSaleStat.cost
      ,
      profit:
      supplierSaleStat.profit
      ,
      statTime:
      supplierSaleStat.statTime
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
    if (this.supplierSaleStat) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    this.onTransmitFormValue.emit({obj: formValue});
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

}
