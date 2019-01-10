import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {ReturnOrderService} from "../../../services/return-order.service";
import {MemberService} from "../../../services/member.service";

@Component({
    selector: 'app-list-return-order',
    templateUrl: './list-return-order.component.html',
    styleUrls: ['./list-return-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListReturnOrderComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public memberService: MemberService, public route: ActivatedRoute, public router: Router, private returnOrderService: ReturnOrderService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, returnOrderService);
        this.buildForm();
    }

    menus = [
        {name: "全部", code: 0, value: ""},
        {name: "待处理", code: 1, value: 1},
        {name: "已处理", code: 2, value: 2},
    ];
    selectMenu: number = 0

    ngOnInit() {
        this.getDatas();
    }

    onItemClick(i) {
        this.pageQuery.clearFilter();
        let elementsByClassName: any = document.getElementsByClassName("filter");
        for (let elementsByClassNameKey in elementsByClassName) {
            elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
        }
        this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('state');
        this.getDatas();
        this.selectMenu = i;
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            returnNo: [null],
            applyTime: [null],
            state: [null],
            username:[null],
            member: {
                id: null,
                username: null
            },
            memberPhone: [null],
            contact: [null],
            contactPhone: [null],
            orderId: [null],
            orderNo: [null],
            orderAmount: [null],
            freight: [null],
            refundAmount: [null],
            returnReason: [null],
            problemDescription: [null],
            voucherPhoto: [null],
            consignee: [null],
            consigneePhone: [null],
            consigneeAddr: [null],
            remark: [null],
            deleted: [null],
            delTime: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            id: null,
            guid: null,
            returnNo: null,
            applyTime: null,
            state: null,
            username:null,
            member: {
                id: null,
                username: null
            },
            memberPhone: null,
            contact: null,
            contactPhone: null,
            orderId: null,
            orderNo: null,
            orderAmount: null,
            freight: null,
            refundAmount: null,
            returnReason: null,
            problemDescription: null,
            voucherPhoto: null,
            consignee: null,
            consigneePhone: null,
            consigneeAddr: null,
            remark: null,
            deleted: null,
            delTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.returnNo!=null) {
            pageQuery.addOnlyFilter('returnNo', searchObj.returnNo, 'contains');
        }
        if (searchObj.username!=null) {
            pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
        }
        return pageQuery;
    }


}
