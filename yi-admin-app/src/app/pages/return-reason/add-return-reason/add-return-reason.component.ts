
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {ReturnReason} from '../../models/original/return-reason.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ReturnReasonService} from '../../../services/return-reason.service';

@Component({
    selector: 'app-add-return-reason',
    templateUrl: './add-return-reason.component.html',
    styleUrls: ['./add-return-reason.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddReturnReasonComponent implements OnInit {

    submitted: boolean = false;

    returnReason: ReturnReason;

    constructor(private router:Router,private returnReasonService: ReturnReasonService, private dialogService: DialogsService) {
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
                this.returnReasonService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/return-reason/list']);
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
