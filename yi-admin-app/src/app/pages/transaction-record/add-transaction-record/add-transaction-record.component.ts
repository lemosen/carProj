import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {TransactionRecord} from '../../models/original/transaction-record.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {TransactionRecordService} from '../../../services/transaction-record.service';

@Component({
    selector: 'app-add-transaction-record',
    templateUrl: './add-transaction-record.component.html',
    styleUrls: ['./add-transaction-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddTransactionRecordComponent implements OnInit {

    submitted: boolean = false;

    transactionRecord: TransactionRecord;

    constructor(private router: Router, private transactionRecordService: TransactionRecordService, private dialogService: DialogsService) {
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
                this.transactionRecordService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/transaction-record/list']);
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
