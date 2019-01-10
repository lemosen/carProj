

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {DailyTask} from '../../models/original/daily-task.model';
import {DailyTaskService} from '../../../services/daily-task.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-daily-task',
  templateUrl: './view-daily-task.component.html',
  styleUrls: ['./view-daily-task.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewDailyTaskComponent implements OnInit {

    dailyTask: DailyTask=new DailyTask;

    constructor(private route: ActivatedRoute,private location: Location,
                private dailyTaskService: DailyTaskService, private dialogService: DialogsService) { }

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

    goBack(){
        this.location.back();
    }

}
