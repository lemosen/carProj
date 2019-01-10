

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { GroupBuyActivityService } from "../../../services/group-buy-activity.service";
import { GroupBuyActivityVo } from '../../../models/domain/vo/group-buy-activity-vo.model';
import { Location } from "@angular/common";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'view-group-buy-activity',
  templateUrl: './view-group-buy-activity.component.html',
  styleUrls: ['./view-group-buy-activity.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewGroupBuyActivityComponent implements OnInit {

    groupBuyActivity: GroupBuyActivityVo=new GroupBuyActivityVo;

    constructor(private route: ActivatedRoute,private location: Location,private groupBuyActivityService: GroupBuyActivityService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.groupBuyActivityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.groupBuyActivity = response.data;
            } else {
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    goBack(){
        this.location.back();
    }

}
