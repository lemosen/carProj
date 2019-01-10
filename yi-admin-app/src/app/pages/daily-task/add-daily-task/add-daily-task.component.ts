
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {DailyTask} from '../../models/original/daily-task.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {DailyTaskService} from '../../../services/daily-task.service';

@Component({
    selector: 'app-add-daily-task',
    templateUrl: './add-daily-task.component.html',
    styleUrls: ['./add-daily-task.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddDailyTaskComponent implements OnInit {

    submitted: boolean = false;

    dailyTask: DailyTask;

    constructor(private router:Router,private dailyTaskService: DailyTaskService, private dialogService: DialogsService) {
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
                this.dailyTaskService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/dailyTask/list']);
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
