import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {SaleOrder} from '../../models/original/sale-order.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SaleOrderService} from '../../../services/sale-order.service';

@Component({
    selector: 'app-add-order-set-up',
    templateUrl: './add-order-set-up.component.html',
    styleUrls: ['./add-order-set-up.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddOrderSetUpComponent implements OnInit {

    submitted: boolean = false;

    saleOrder: SaleOrder;

    constructor(private router: Router, private saleOrderService: SaleOrderService, private dialogService: DialogsService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }

    submitForm(formValue) {
        // if (this.submitted) {
        //     return;
        // }
        this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                this.submitted = true;
                this.saleOrderService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/sale-order/list']);
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        }, () => {
            this.submitted = false;
        });
    }
}
