
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {ReturnOrder} from '../../models/original/return-order.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ReturnOrderService} from '../../../services/return-order.service';

@Component({
    selector: 'app-add-return-order',
    templateUrl: './add-return-order.component.html',
    styleUrls: ['./add-return-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddReturnOrderComponent implements OnInit {

    submitted: boolean = false;

    returnOrder: ReturnOrder;

    constructor(private router:Router,private returnOrderService: ReturnOrderService, private dialogService: DialogsService) {
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
                this.returnOrderService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/returnOrder/list']);
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
