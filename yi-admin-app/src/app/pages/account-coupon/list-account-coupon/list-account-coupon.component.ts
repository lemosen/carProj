import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AccountCouponService} from '../../../services/account-coupon.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-account-coupon',
    templateUrl: './list-account-coupon.component.html',
    styleUrls: ['./list-account-coupon.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAccountCouponComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private accountCouponService: AccountCouponService, public dialogService: DialogsService) {
        super(route, router, dialogService, accountCouponService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
