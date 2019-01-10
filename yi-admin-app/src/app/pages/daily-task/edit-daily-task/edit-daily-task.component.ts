

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {DailyTaskService} from '../../../services/daily-task.service';
import {DailyTask} from '../../models/original/daily-task.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-daily-task',
  templateUrl: './edit-daily-task.component.html',
  styleUrls: ['./edit-daily-task.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditDailyTaskComponent implements OnInit {

  submitted: boolean = false;

  dailyTask: DailyTask;

  constructor(private route: ActivatedRoute,private router:Router,private dailyTaskService: DailyTaskService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.dailyTaskService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.dailyTask = response.data;
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
                this.dailyTaskService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
