import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {AccountCoupon} from '../../models/original/account-coupon.model';
import {AccountCouponService} from '../../../services/account-coupon.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-account-coupon',
    templateUrl: './view-account-coupon.component.html',
    styleUrls: ['./view-account-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewAccountCouponComponent implements OnInit {

    accountCoupon: AccountCoupon = new AccountCoupon;

    constructor(private route: ActivatedRoute, private location: Location,
                private accountCouponService: AccountCouponService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.accountCouponService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.accountCoupon = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    goBack() {
        this.location.back();
    }

}
