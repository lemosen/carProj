import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Withdraw} from '../../models/original/withdraw.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {WithdrawService} from '../../../services/withdraw.service';

@Component({
    selector: 'app-add-withdraw',
    templateUrl: './add-withdraw.component.html',
    styleUrls: ['./add-withdraw.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddWithdrawComponent implements OnInit {

    submitted: boolean = false;

    withdraw: Withdraw;

    constructor(private router: Router, private withdrawService: WithdrawService, private dialogService: DialogsService) {
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
                this.withdrawService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/withdraw/list']);
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
