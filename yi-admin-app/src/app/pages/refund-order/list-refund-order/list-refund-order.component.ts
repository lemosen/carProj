import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {RefundOrderService} from '../../../services/refund-order.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {MemberService} from "../../../services/member.service";

@Component({
    selector: 'app-list-refund-order',
    templateUrl: './list-refund-order.component.html',
    styleUrls: ['./list-refund-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRefundOrderComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public memberService:MemberService,public route: ActivatedRoute, public router: Router, private refundOrderService: RefundOrderService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, refundOrderService);
        this.buildForm();
    }

    menus = [
        {name: "全部", code: 0, value: ""},
        {name: "待处理", code: 1, value: 1},
        {name: "已处理", code: 2, value: 2},
    ];
    selectMenu: number = 0

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

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            refundNo: [null],
            applyTime: [null],
            state: [null],
            username:[null],
            member: {
                id:null,
                username:null
            },
            memberPhone: [null],
            orderId: [null],
            orderNo: [null],
            orderAmount: [null],
            refundAmount: [null],
            refundMode: [null],
            refundType: [null],
            refundReason: [null],
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
            refundNo: null,
            applyTime: null,
            state: null,
            username:null,
            member:{
                id:null,
                username:null
            },
            memberPhone: null,
            orderId: null,
            orderNo: null,
            orderAmount: null,
            refundAmount: null,
            refundMode: null,
            refundType: null,
            refundReason: null,
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
        if (searchObj.refundNo!=null) {
            pageQuery.addOnlyFilter('refundNo', searchObj.refundNo, 'contains');
        }
        if (searchObj.username!=null) {
            pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
        }
        return pageQuery;
    }


}
