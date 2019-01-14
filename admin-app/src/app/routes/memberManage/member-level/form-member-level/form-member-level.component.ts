import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MemberLevel} from '../../../models/original/member-level.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';

@Component({
  selector: 'form-member-level',
  templateUrl: './form-member-level.component.html',
  styleUrls: ['./form-member-level.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormMemberLevelComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() memberLevel: MemberLevel = new MemberLevel();

  formatterDiscount = value => `${value} %`;
  parserDiscount = value => value.replace(' %', '');

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  formErrors = {

    name: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    growthValue: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    discount: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大5位长度',
      }
    ],
    defaulted: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    rank: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.memberLevel !== undefined && value.memberLevel.currentValue !== undefined) {
      this.setBuildFormValue(this.memberLevel);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      name: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      growthValue: [10e8, Validators.compose([Validators.required, Validators.maxLength(10)])],
      discount: [100, Validators.compose([Validators.required, Validators.maxLength(5)])],
      defaulted: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      rank: [1, Validators.compose([Validators.required, Validators.min(1)])],
      countNumber: [1, Validators.compose([Validators.required, Validators.min(1)])],
      bonusBalance: [1, Validators.compose([Validators.required, Validators.min(1)])],
      fristRoyalty: [1, Validators.compose([Validators.required, Validators.min(1)])],
      secondRoyalty: [1, Validators.compose([Validators.required, Validators.min(1)])],
    });
  }

  setBuildFormValue(memberLevel: MemberLevel) {
    this.commonForm.setValue({
      name: memberLevel.name,
      growthValue: memberLevel.growthValue,
      discount: memberLevel.discount,
      defaulted: memberLevel.defaulted,
      rank: memberLevel.rank,
      countNumber:memberLevel.countNumber,
      bonusBalance:memberLevel.bonusBalance,
      fristRoyalty:memberLevel.fristRoyalty,
      secondRoyalty:memberLevel.secondRoyalty,
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
