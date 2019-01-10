
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {RefundOrder} from '../../models/original/refund-order.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {RefundOrderService} from '../../../services/refund-order.service';

@Component({
    selector: 'app-add-refund-order',
    templateUrl: './add-refund-order.component.html',
    styleUrls: ['./add-refund-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddRefundOrderComponent implements OnInit {

    submitted: boolean = false;

    refundOrder: RefundOrder;

    constructor(private router:Router,private refundOrderService: RefundOrderService, private dialogService: DialogsService) {
    }

    ngOnInit() {
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
        this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                this.submitted = true;
                this.refundOrderService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/refundOrder/list']);
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
