
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {OrderSetting} from '../../models/original/order-setting.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {OrderSettingService} from '../../../services/order-setting.service';

@Component({
    selector: 'app-add-order-setting',
    templateUrl: './add-order-setting.component.html',
    styleUrls: ['./add-order-setting.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddOrderSettingComponent implements OnInit {

    submitted: boolean = false;

    orderSetting: OrderSetting;

    constructor(private router:Router,private orderSettingService: OrderSettingService, private dialogService: DialogsService) {
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
                this.orderSettingService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/order-setting/list']);
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
