
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {AccountRecord} from '../../models/original/account-record.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AccountRecordService} from '../../../services/account-record.service';

@Component({
    selector: 'app-add-account-record',
    templateUrl: './add-account-record.component.html',
    styleUrls: ['./add-account-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAccountRecordComponent implements OnInit {

    submitted: boolean = false;

    accountRecord: AccountRecord;

    constructor(private router:Router,private accountRecordService: AccountRecordService, private dialogService: DialogsService) {
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
                this.accountRecordService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/accountRecord/list']);
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
