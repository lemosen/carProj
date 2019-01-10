

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {CouponUse} from '../../models/original/coupon-use.model';
import {CouponUseService} from '../../../services/coupon-use.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-coupon-use',
  templateUrl: './view-coupon-use.component.html',
  styleUrls: ['./view-coupon-use.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCouponUseComponent implements OnInit {

    couponUse: CouponUse=new CouponUse;

    constructor(private route: ActivatedRoute,private location: Location,
                private couponUseService: CouponUseService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponUseService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.couponUse = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    goBack(){
        this.location.back();
    }

}
