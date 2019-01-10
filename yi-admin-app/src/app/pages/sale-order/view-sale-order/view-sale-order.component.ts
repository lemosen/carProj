import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {SaleOrderService} from '../../../services/sale-order.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';
import {SaleOrder} from "../../models/original/sale-order.model";
import {Router} from "@angular/router";

@Component({
    selector: 'app-view-sale-order',
    templateUrl: './view-sale-order.component.html',
    styleUrls: ['./view-sale-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewSaleOrderComponent implements OnInit {

    saleOrder: SaleOrder = new SaleOrder;

    constructor(private router: Router, private route: ActivatedRoute, private location: Location,
        private saleOrderService: SaleOrderService, private dialogService: DialogsService) {
    }

    orderDetails = [
        { title: "1、待付款>", index: 1 },
        { title: "2、待发货>", index: 2 },
        { title: "3、已发货>", index: 3 },
        { title: "4、已完成>", index: 4 },
    ];

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.saleOrderService.getById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.saleOrder = response.data;
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
    goUpdateOrderState(id, state) {
        this.dialogService.confirm('提示', '确定要执行吗？').then((result) => {
            if (result) {
                this.saleOrderService.updateOrderState(id, state).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.router.navigate(['/pages/sale-order/list']);
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
