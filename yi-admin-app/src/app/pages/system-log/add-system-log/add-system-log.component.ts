import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {SystemLog} from '../../models/original/system-log.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SystemLogService} from '../../../services/system-log.service';

@Component({
    selector: 'app-add-system-log',
    templateUrl: './add-system-log.component.html',
    styleUrls: ['./add-system-log.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSystemLogComponent implements OnInit {

    submitted: boolean = false;

    systemLog: SystemLog;

    constructor(private router: Router, private systemLogService: SystemLogService, private dialogService: DialogsService) {
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
                this.systemLogService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/systemLog/list']);
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
