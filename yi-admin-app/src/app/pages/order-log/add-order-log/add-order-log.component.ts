
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {OrderLog} from '../../models/original/order-log.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {OrderLogService} from '../../../services/order-log.service';

@Component({
    selector: 'app-add-order-log',
    templateUrl: './add-order-log.component.html',
    styleUrls: ['./add-order-log.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddOrderLogComponent implements OnInit {

    submitted: boolean = false;

    orderLog: OrderLog;

    constructor(private router:Router,private orderLogService: OrderLogService, private dialogService: DialogsService) {
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
                this.orderLogService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/orderLog/list']);
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
