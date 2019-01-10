import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {TransactionRecordService} from '../../../services/transaction-record.service';
import {TransactionRecord} from '../../models/original/transaction-record.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-transaction-record',
    templateUrl: './edit-transaction-record.component.html',
    styleUrls: ['./edit-transaction-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditTransactionRecordComponent implements OnInit {

    submitted: boolean = false;

    transactionRecord: TransactionRecord;

    constructor(private route: ActivatedRoute, private router: Router, private transactionRecordService: TransactionRecordService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.transactionRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.transactionRecord = response.data;
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
                this.transactionRecordService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
