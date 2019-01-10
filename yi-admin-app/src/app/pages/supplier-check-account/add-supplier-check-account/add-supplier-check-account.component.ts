
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {SupplierCheckAccount} from '../../models/original/supplier-check-account.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SupplierCheckAccountService} from '../../../services/supplier-check-account.service';

@Component({
    selector: 'app-add-supplier-check-account',
    templateUrl: './add-supplier-check-account.component.html',
    styleUrls: ['./add-supplier-check-account.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSupplierCheckAccountComponent implements OnInit {

    submitted: boolean = false;

    supplierCheckAccount: SupplierCheckAccount;

    constructor(private router:Router,private supplierCheckAccountService: SupplierCheckAccountService, private dialogService: DialogsService) {
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
                this.supplierCheckAccountService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/supplierCheckAccount/list']);
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
