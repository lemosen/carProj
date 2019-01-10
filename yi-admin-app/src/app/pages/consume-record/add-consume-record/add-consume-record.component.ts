
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {ConsumeRecord} from '../../models/original/consume-record.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ConsumeRecordService} from '../../../services/consume-record.service';

@Component({
    selector: 'app-add-consume-record',
    templateUrl: './add-consume-record.component.html',
    styleUrls: ['./add-consume-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddConsumeRecordComponent implements OnInit {

    submitted: boolean = false;

    consumeRecord: ConsumeRecord;

    constructor(private router:Router,private consumeRecordService: ConsumeRecordService, private dialogService: DialogsService) {
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
                this.consumeRecordService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/consume-record/list']);
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
