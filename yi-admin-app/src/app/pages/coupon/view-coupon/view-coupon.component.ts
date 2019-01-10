

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Coupon} from '../../models/original/coupon.model';
import {CouponService} from '../../../services/coupon.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-coupon',
  templateUrl: './view-coupon.component.html',
  styleUrls: ['./view-coupon.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCouponComponent implements OnInit {

    coupon: Coupon=new Coupon;

    constructor(private route: ActivatedRoute,private location: Location,
                private couponService: CouponService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.coupon = response.data;
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
