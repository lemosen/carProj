import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {RefundService} from '../../../services/refund.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-refund',
    templateUrl: './list-refund.component.html',
    styleUrls: ['./list-refund.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRefundComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private refundService: RefundService, public dialogService: DialogsService) {
        super(route, router, dialogService, refundService);
    }

    refundMoney =[{
        code: "false",
        title: "false",
    }, {
        code: "true",
        title: "true",
    }]

    refundStates = [{
        code: "false",
        title: "待退货",
    }, {
        code: "true",
        title: "已退货",
    }]

    valueChange(value) {
        console.log(value)
    }

    selItem: number = 0;

    menus = [
        {name: "全部", code: 0 },
        {name: "待处理", code: 1, value: "false"},
        {name: "已处理", code: 2, value: "true"},
        {name: "已拒绝", code: 3, value: "true"},
    ];
    selectMenu: number = 0

    onItemClick(i) {
        this.pageQuery.clearFilter();
        let elementsByClassName: any = document.getElementsByClassName("filter");
        for (let elementsByClassNameKey in elementsByClassName) {
            elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
        }
        this.pageQuery.addOnlyFilter('isReturn', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('isReturn');
        this.getDatas();
        this.selectMenu = i;
    }


    ngOnInit() {
        this.getDatas();
    }
}
