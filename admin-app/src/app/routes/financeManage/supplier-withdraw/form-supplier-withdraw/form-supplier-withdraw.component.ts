import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SupplierWithdraw} from '../../../models/original/supplier-withdraw.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-supplier-withdraw',
  templateUrl: './form-supplier-withdraw.component.html',
  styleUrls: ['./form-supplier-withdraw.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormSupplierWithdrawComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() supplierWithdraw: SupplierWithdraw = new SupplierWithdraw();

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
    supplierId: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    supplierName: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    amount: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大15位长度',
      }
    ],
    cardType: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    cardNo: [
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    payee: [
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
    errorDescription: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    applyTime: [
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    dealTime: [
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
    if (value.supplierWithdraw !== undefined && value.supplierWithdraw.currentValue !== undefined) {
      this.setBuildFormValue(this.supplierWithdraw);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      guid: [],
      supplierId: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      supplierName: [],
      amount: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(15)
        ])
      ],
      cardType: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(3)
        ])
      ],
      cardNo: [],
      payee: [],
      state: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(3)
        ])
      ],
      errorDescription: [],
      applyTime: [],
      dealTime: [],
    });
  }

  setBuildFormValue(supplierWithdraw: SupplierWithdraw) {
    this.commonForm.setValue({
      guid:
      supplierWithdraw.guid
      ,
      supplierId:
      supplierWithdraw.supplierId
      ,
      supplierName:
      supplierWithdraw.supplierName
      ,
      amount:
      supplierWithdraw.amount
      ,
      cardType:
      supplierWithdraw.cardType
      ,
      cardNo:
      supplierWithdraw.cardNo
      ,
      payee:
      supplierWithdraw.payee
      ,
      state:
      supplierWithdraw.state
      ,
      errorDescription:
      supplierWithdraw.errorDescription
      ,
      applyTime:
      supplierWithdraw.applyTime
      ,
      dealTime:
      supplierWithdraw.dealTime
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
    if (this.supplierWithdraw) {
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
