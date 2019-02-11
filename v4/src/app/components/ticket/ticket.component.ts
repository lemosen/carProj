import {Component, Input, OnInit} from '@angular/core';
import {Coupon} from "../../../domain/original/coupon.model";

@Component({
    selector: 'ticket',
    templateUrl: './ticket.component.html',
    styleUrls: ['./ticket.component.scss']
})
export class TicketComponent implements OnInit {
    @Input()
    ticket: any;

    /*储值券部分字体颜色的改动*/
    @Input()
    color: string;

    /*个人中心和购物车进入时的传值*/
    @Input()
    type: string = 'true';

    /*已选优惠券Id*/
    @Input()
    checkId: number;

    selectedCoupon;

    cornerImg = ["", "../../assets/app_icon/customer/角标满@3x.png", "../../assets/app_icon/customer/角标赠@3x.png", "../../assets/app_icon/customer/角标储@3x.png"];
    typeImg = ["", "../../assets/app_icon/customer/满减券标签@3x.png", "../../assets/app_icon/customer/买送券标签@3x.png", "../../assets/app_icon/customer/储值券标签@3x.png"]

    constructor() {
    }

    ngOnInit() {
    }

    isSelected(item) {
        /*ionc4，需要延时，否则会出现问题*/
        setTimeout(() => {
            if (this.checkId == item.id) {
                this.checkId = undefined;
                this.selectedCoupon = "cancel";
            } else {
                this.checkId = item.id;
                this.selectedCoupon = this.ticket.find(e => (e.id == item.id));
            }
        }, 0)
    }

    /*使用条件提示*/
    getConditionContent(useConditionType, fullMoney, fullQuantity) {
        switch (useConditionType) {
            case 0 :
                return "&nbsp;";
            case 1 :
                return "满" + fullMoney + "元可用";
            case 2 :
                return "满" + fullQuantity + "件可用";
            default:
                return "&nbsp;"
        }
    }
}
