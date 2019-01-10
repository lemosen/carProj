import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {TransactionRecord} from '../../models/original/transaction-record.model';
import {TransactionRecordService} from '../../../services/transaction-record.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-transaction-record',
    templateUrl: './view-transaction-record.component.html',
    styleUrls: ['./view-transaction-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewTransactionRecordComponent implements OnInit {

    transactionRecord: TransactionRecord = new TransactionRecord;

    constructor(private route: ActivatedRoute, private location: Location,
                private transactionRecordService: TransactionRecordService, private dialogService: DialogsService) {
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

    goBack() {
        this.location.back();
    }

}
