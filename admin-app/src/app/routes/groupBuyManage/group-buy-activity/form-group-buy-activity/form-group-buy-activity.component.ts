import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GroupBuyActivityBo} from '../../../models/domain/bo/group-buy-activity-bo.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {ProductService} from "../../../services/product.service";
import {PageQuery} from "../../../models/page-query.model";
import {MemberLevelService} from "../../../services/member-level.service";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";
import {UserService} from "../../../services/user.service";
import {CommodityService} from "../../../services/commodity.service";

@Component({
  selector: 'form-group-buy-activity',
  templateUrl: './form-group-buy-activity.component.html',
  styleUrls: ['./form-group-buy-activity.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormGroupBuyActivityComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() groupBuyActivity: GroupBuyActivityBo = new GroupBuyActivityBo();
  memberLevelDatas: any[] = [];

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  commodityPageQuery: PageQuery = new PageQuery();
  productPageQuery: PageQuery = new PageQuery();
  memberLevelPageQuery: PageQuery = new PageQuery();
  userPageQuery: PageQuery = new PageQuery();
  groupBuyActivityTimesArryForm: FormArray;
  dateFormat = 'yyyy/MM/dd';
  @ViewChild('modalSelectMember') modalSelectMember: ModalSelecetComponent;

  formErrors = {

    activityName: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大128位长度',
      }
    ],
    coverUrl: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    priority: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    publishUser: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    memberType: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    memberLevels: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    type: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    hasPost: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    hasCoupon: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public productService: ProductService, public memberLevelService: MemberLevelService, public userService: UserService, public commodityService: CommodityService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.groupBuyActivity !== undefined && value.groupBuyActivity.currentValue !== undefined) {
      this.setBuildFormValue(this.groupBuyActivity);
    }
  }

  ngOnInit() {
    this.commodityPageQuery.addOnlyOneFilter("state", 2, "eq");
    this.commodityPageQuery.addOnlyOneFilter("shelf", 1, "eq");
    this.userPageQuery.addOnlyOneFilter("state", 0, "eq")
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      activityName: [null, Validators.compose([Validators.required, Validators.maxLength(128)])],
      coverUrl: [null, Validators.compose([Validators.required])],
      priority: [null, Validators.compose([Validators.required, Validators.maxLength(3)])],
      publishUser: [null, Validators.compose([Validators.required])],
      memberType: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
      memberLevels: [null],
      type: [1, Validators.compose([Validators.required, Validators.min(1)])],
      hasPost: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
      hasCoupon: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      groupBuyActivityMember: {
        memberType: 1,
        memberLevel: null,
      },
      groupBuyActivityTimes: this.fb.array([this.fb.group({
        id: 0,
        groupBuyActivity: null,
        startTime: null,
        endTime: null,
        timeSlot: [[]],
      })]),
      groupBuyActivityProducts: this.fb.array([this.fb.group({
        id: 0,
        groupBuyActivity: null,
        commodity: null,
        product: null,
        groupBuyPrice: 0,
        groupBuyQuantity: 0,
        stockUpQuantity: 0,
      })]),
    });
    this.groupBuyActivityTimesArryForm = this.commonForm.get('groupBuyActivityTimes') as FormArray
  }

  setBuildFormValue(groupBuyActivity: GroupBuyActivityBo) {

    let groupBuyActivityProducts: FormArray = this.commonForm.get('groupBuyActivityProducts') as FormArray;
    groupBuyActivityProducts.removeAt(0);
    groupBuyActivity.groupBuyActivityProducts.forEach(e => {
      groupBuyActivityProducts.push(this.fb.group({
        id: e.id,
        groupBuyActivity: e.groupBuyActivity,
        commodity: e.commodity,
        product: e.product,
        groupBuyPrice: e.groupBuyPrice,
        groupBuyQuantity: e.groupBuyQuantity,
        stockUpQuantity: e.stockUpQuantity,
      }))
    })
    this.commonForm.setControl('groupBuyActivityProducts', groupBuyActivityProducts);

    this.commonForm.patchValue({
      activityName: groupBuyActivity.activityName,
      coverUrl: groupBuyActivity.coverUrl,
      priority: groupBuyActivity.priority,
      publishUser: {
        id: groupBuyActivity.publishUser.id,
        fullName: groupBuyActivity.publishUser.fullName,
      },
      memberType: groupBuyActivity.groupBuyActivityMember.memberType,
      memberLevels: null,
      type: groupBuyActivity.type,
      hasPost: groupBuyActivity.hasPost,
      hasCoupon: groupBuyActivity.hasCoupon,
      groupBuyActivityMember: groupBuyActivity.groupBuyActivityMember,
    });

    let datas: any[] = [];
    this.memberLevelService.query(this.memberLevelPageQuery).subscribe(response => {
      this.memberLevelDatas = response['content'];
      this.memberLevelDatas.forEach(e => {
        let memberLevelArray = groupBuyActivity.groupBuyActivityMember.memberLevel.split(",");
        memberLevelArray.forEach(e1 => {
          if (e.id == e1) {
            datas.push({id: e.id, name: e.name});
          }
        })
      })
      this.commonForm.patchValue({
        memberLevels: datas,
      })
      this.modalSelectMember.dataRetrieval(this.commonForm.value.memberLevels)
    }, error => {
      this.msgSrv.error('会员等级获取失败' + error.message);
    });

    let groupBuyActivityTimes: FormArray = this.commonForm.get('groupBuyActivityTimes') as FormArray;
    groupBuyActivityTimes.removeAt(0);
    groupBuyActivity.groupBuyActivityTimes.forEach(e => {
      groupBuyActivityTimes.push(this.fb.group({
        id: e.id,
        groupBuyActivity: e.groupBuyActivity,
        startTime: e.startTime,
        endTime: e.endTime,
        timeSlot: [[new Date(e.startTime), new Date(e.endTime)]],
      }))
    })
    this.commonForm.setControl('groupBuyActivityTimes', groupBuyActivityTimes);

  }

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
    if (commonFormValid) {
      return this.commonForm.value;
    }
    return null;
  }


  onSubmit() {
    if (this.commonForm.value.memberType == 1) {
      this.commonForm.patchValue({
        groupBuyActivityMember: {
          memberType: 1,
          memberLevel: null,
        }
      })
    } else {
      let memberlevel = "";
      this.commonForm.value.memberLevels.forEach((e, index) => {
        if (index == this.commonForm.value.memberLevels.length) {
          memberlevel = e.id;
        } else {
          memberlevel = e.id + "," + memberlevel;
        }
      })
      this.commonForm.patchValue({
        groupBuyActivityMember: {
          memberType: 2,
          memberLevel: memberlevel,
        }
      })
    }
    const formValue = this.submitCheck();
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    // console.log("commonForm value=" + JSON.stringify(formValue));
    this.onTransmitFormValue.emit({obj: formValue});
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  getPic(event) {
    this.commonForm.patchValue({
      coverUrl: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
    })
  }

  setCommodity(event, item, i) {
    if (event.commodityName) {
      item.patchValue({
        commodity: event
      })
      this.productPageQuery.addOnlyOneFilter("commodity.id", event.id, "eq");
      this.productPageQuery.addLockFilterName("commodity.id");
    }
  }

  setProduct(event, item) {
    if (event.productName) {
      item.patchValue({
        product: event
      })
    }
  }

  setUser(event) {
    if (event.fullName) {
      this.commonForm.patchValue({
        publishUser: {
          id: event.id,
          fullName: event.fullName,
        }
      });
    }
  }

  setMemberLevel(event) {
    this.commonForm.patchValue({
      memberLevels: event.map(e => {
        return {id: e.id, name: e.name, discount: e.discount}
      })
    });
  }

  selectTime(e, form) {
    if (e != null) {
      let split = e[0].toLocaleDateString().split('/');
      let split1 = e[1].toLocaleDateString().split('/');
      let startTime = split[0] + "-" + split[1] + "-" + split[2];
      let endTime = split1[0] + "-" + split1[1] + "-" + split1[2];
      form.patchValue({
        startTime: startTime,
        endTime: endTime,
      })
    }
  }

  deleteTime(i, formCtrlName) {
    let groupBuyActivityTimes: FormArray = this.commonForm.get('groupBuyActivityTimes') as FormArray;
    if (groupBuyActivityTimes.length == 1) {
      this.msgSrv.warning('活动时间不能为空');
      return;
    }
    groupBuyActivityTimes.removeAt(i)
  }

  addNewTime(formCtrlName) {
    let groupBuyActivityTimes: FormArray = this.commonForm.get('groupBuyActivityTimes') as FormArray;
    groupBuyActivityTimes.push(this.fb.group({
      id: 0,
      groupBuyActivity: null,
      startTime: null,
      endTime: null,
      timeSlot: [[]],
    }))
  }

  private _remove(i, formCtrlName) {
    let groupBuyActivityProducts = this.commonForm.get(formCtrlName) as FormArray;
    groupBuyActivityProducts.removeAt(i)
  }

  private _addCustomFreight(formCtrlName) {
    let groupBuyActivityProducts = this.commonForm.get(formCtrlName) as FormArray;
    groupBuyActivityProducts.push(
      this.fb.group({
        id: 0,
        groupBuyActivity: null,
        commodity: null,
        product: null,
        groupBuyPrice: 0,
        groupBuyQuantity: 0,
        stockUpQuantity: 0,
      }))
  }

}
