import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'app-coupon',
    templateUrl: './coupon.component.html',
    styleUrls: ['./coupon.component.scss']
})
export class CouponComponent implements OnInit {

    @Input()
    tickets: any;


    cornerImg = ["", "../../assets/app_icon/customer/角标满@3x.png", "../../assets/app_icon/customer/角标赠@3x.png", "../../assets/app_icon/customer/角标储@3x.png"];
    typeImg = ["", "../../assets/app_icon/customer/满减券标签@3x.png", "../../assets/app_icon/customer/买送券标签@3x.png", "../../assets/app_icon/customer/储值券标签@3x.png"]


    constructor() {
    }

    ngOnInit() {
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
