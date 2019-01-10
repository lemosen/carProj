import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {WithdrawService} from '../../../services/withdraw.service';
import {Withdraw} from '../../models/original/withdraw.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-withdraw',
    templateUrl: './edit-withdraw.component.html',
    styleUrls: ['./edit-withdraw.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditWithdrawComponent implements OnInit {

    submitted: boolean = false;

    withdraw: Withdraw;

    constructor(private route: ActivatedRoute, private router: Router, private withdrawService: WithdrawService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.withdrawService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.withdraw = response.data;
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
                formValue.obj.withdrawId=this.withdraw.withdrawId;
                this.withdrawService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
