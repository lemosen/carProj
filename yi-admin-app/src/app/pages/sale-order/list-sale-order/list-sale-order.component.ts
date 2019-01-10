import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {SaleOrderService} from '../../../services/sale-order.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";
import {MemberService} from "../../../services/member.service";

@Component({
    selector: 'app-list-sale-order',
    templateUrl: './list-sale-order.component.html',
    styleUrls: ['./list-sale-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListSaleOrderComponent extends CommonList implements OnInit {

    searchForm: FormGroup;


    constructor(public memberService: MemberService, public route: ActivatedRoute, public router: Router, private saleOrderService: SaleOrderService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, saleOrderService);
        this.buildForm();
    }

    menus = [
        { name: "全部订单", code: 0, value: "" },
        { name: "待付款", code: 1, value: 1 },
        { name: "待发货", code: 2, value: 2 },
        { name: "已发货", code: 3, value: 3 },
        { name: "已完成", code: 4, value: 4 },
        { name: "已关闭", code: 5, value: 5 },
    ];
    selectMenu: number = 0

    payModes = [{
        code: 4,
        title: "待支付",
    }, {
            code: 1,
            title: "支付宝",
        }, {
            code: 2,
            title: "微信",
        }, {
            code: 3,
            title: "银联",
        }]

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

    setSaleOrderMember(event) {
        console.log(event);
        this.searchForm.patchValue({
            member: {
                id: event.id,
                username: event.username,
            }
        });
    }

    ngOnInit() {
        this.getDatas();
    }

    selectVaule(type, value) {
        this.searchForm.get(type).setValue(value);
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            username: [null],
            member: {
                id: null,
                username: null
            },
            orderNo: [null],
            state: [null],
            buyerMessage: [null],
            consignee: [null],
            consigneePhone: [null],
            consigneeAddr: [null],
            deliveryMode: [null],
            logisticsCompany: [null],
            trackingNo: [null],
            orderAmount: [null],
            discountAmount: [null],
            freight: [null],
            payAmount: [null],
            tradeNo: [null],
            payMode: [null],
            createTime: [null],
            paymentTime: [null],
            deliveryTime: [null],
            dealTime: [null],
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
            username: null,
            member: {
                id: null,
                username: null
            },
            orderNo: null,
            state: null,
            buyerMessage: null,
            consignee: null,
            consigneePhone: null,
            consigneeAddr: null,
            deliveryMode: null,
            logisticsCompany: null,
            trackingNo: null,
            orderAmount: null,
            discountAmount: null,
            freight: null,
            payAmount: null,
            tradeNo: null,
            payMode: null,
            createTime: null,
            paymentTime: null,
            deliveryTime: null,
            dealTime: null,
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
        if (searchObj.username != null) {
            pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
        }
        if (searchObj.orderNo != null) {
            pageQuery.addOnlyFilter('orderNo', searchObj.orderNo, 'contains');
        }
        if (searchObj.payMode != null) {
            pageQuery.addOnlyFilter('payMode', searchObj.payMode, 'eq');
        }
        /*if (searchObj.createTime != null) {
            pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
        }*/
        return pageQuery;
    }

    goUpdateOrderState(id, state) {
        this.dialogService.confirm('提示', '确定要执行吗？').then((result) => {
            if (result) {
                this.saleOrderService.updateOrderState(id, state).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.getDatas();
                     } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                })
            }
        }, () => {
        });
    }

}
