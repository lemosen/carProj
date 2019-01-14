import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Recommend} from '../../../models/original/recommend.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";
import {PageQuery} from "../../../models/page-query.model";
import {PositionService} from "../../../services/position.service";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
  selector: 'form-recommend',
  templateUrl: './form-recommend.component.html',
  styleUrls: ['./form-recommend.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormRecommendComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() recommend: Recommend = new Recommend();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('modalSelect') modalSelect: ModalSelecetComponent;
  commodityPageQuery: PageQuery = new PageQuery();
  positionPageQuery: PageQuery = new PageQuery();

  formErrors = {

    position: [
      {
        name: 'required',
        msg: '不可为空',
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
    imgPath: [
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    sort: [],
    recommendType: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    showMode: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
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

    showBanner: [{
      name: 'required',
      msg: '不可为空',
    },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }],
    showTitle: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    commodities: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService, public msg: NzMessageService, public positionService: PositionService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.recommend !== undefined && value.recommend.currentValue !== undefined) {
      this.setBuildFormValue(this.recommend);
    }
  }

  ngOnInit() {
    this.positionPageQuery.addOnlyFilter("positionType", "1", "neq")
    this.positionPageQuery.addOnlyFilter("state", "0", "eq")
    this.commodityPageQuery.addFilter("state", 2, "eq");
    this.commodityPageQuery.addFilter("shelf", 1, "eq");

  }

  getPic(event) {
    this.commonForm.patchValue({
      imgPath: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
    })
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      position: [null, Validators.compose([Validators.required])],
      title: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
      sort: [],
      imgPath: [],
      recommendType: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
      showMode: [2, Validators.compose([Validators.required, Validators.min(2), Validators.max(4)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      commodities: [null, Validators.compose([Validators.required])],
      showBanner: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      showTitle: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])]
    });
  }


  setBuildFormValue(recommend: Recommend) {
    this.commonForm.setValue({
      position: {
        id: recommend.position.id,
        name: recommend.position.name
      },
      title: recommend.title,
      commodities: recommend.commodities,
      sort: recommend.sort,
      imgPath: recommend.imgPath,
      recommendType: recommend.recommendType,
      showMode: recommend.showMode,
      state: recommend.state,
      showBanner: recommend.showBanner,
      showTitle: recommend.showTitle
    });
    this.modalSelect.dataRetrieval(recommend.commodities);

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

  setCommodity(event) {
    this.commonForm.patchValue({
      commodities: event.map(e => {
        return {id: e.id, commodityShortName: e.commodityShortName, commodityNo: e.commodityNo, imgPath: e.imgPath}
      })
    });
  }

  setRecommendPosition(event) {
    if(event.name){
      this.commonForm.patchValue({
        position: {
          id: event.id,
          name: event.name,
        }
      });
    }
  }

}
