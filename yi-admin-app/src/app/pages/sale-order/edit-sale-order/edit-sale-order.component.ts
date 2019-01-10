import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SaleOrderService} from '../../../services/sale-order.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {SaleOrder} from "../../models/original/sale-order.model";

@Component({
    selector: 'app-edit-sale-order',
    templateUrl: './edit-sale-order.component.html',
    styleUrls: ['./edit-sale-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditSaleOrderComponent implements OnInit {

    submitted: boolean = false;

    saleOrder: SaleOrder;

    constructor(private route: ActivatedRoute, private router: Router, private saleOrderService: SaleOrderService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.saleOrderService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.saleOrder = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }

    submitForm(formValue) {
        if (this.submitted) {
            return;
        }
        this.dialogService.confirm('提示', '确认要提交吗？').then(result => {
            if (result) {
                this.submitted = true;
                this.saleOrderService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/order/list']);
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        });
    }
}
