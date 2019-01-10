import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SaleOrderService} from '../../../services/sale-order.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";
import {FormArray, FormGroup} from "@angular/forms";

@Component({
    selector: 'app-list-order-set-up',
    templateUrl: './list-order-set-up.component.html',
    styleUrls: ['./list-order-set-up.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListOrderSetUpComponent extends CommonList implements OnInit {


    orderSetUpFrom:FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private saleOrderService: SaleOrderService, public dialogService: DialogsService) {
        super(route, router, dialogService, saleOrderService);
    }

    ngOnChanges(value) {
        /*if (value.refund !== undefined && value.refund.currentValue !== undefined) {
            this.setBuildFormValue(this.refund);
        }*/
    }


    valueChange(value) {
        console.log(value)
    }


    ngOnInit() {
        this.getDatas();
    }

    onSubmit(){
        if (this.orderSetUpFrom.get("supplier.supplierName").value == "请选择") {
            this.dialogService.toast('warning', '提示', '请选择供应商！');
            return;
        }
        /*if (this.commonForm.value.freightSet == 1) {
            if (this.commonForm.get("unifiedFreight").value == null) {
                this.dialogService.toast('warning', '提示', '请输入统一运费！');
                return;
            }
        }
        if (this.commonForm.value.freightSet == 2) {
            if (this.commonForm.get("freightTemplate.templateName").value == "请选择") {
                this.dialogService.toast('warning', '提示', '请选择运费模板！');
                return;
            }
        }
        if (this.attrArryForm.controls.length == 0) {
            this.dialogService.toast('warning', '提示', '请添加商品属性！');
            return;
        }
        if (this.attrArryForm.controls.length != 0) {
            if (this.attrArryForm.value[0].groupName == '') {
                this.dialogService.toast('warning', '提示', '请填写属性名');
                return;
            } else {
                if(this.attrArryForm.value[0].attributes[0].attrValue== ''){
                    this.dialogService.toast('warning', '提示', '请填写属性值');
                    return;
                }
            }
        }*/
    }
}
