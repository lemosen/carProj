import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {SystemLog} from '../../models/original/system-log.model';
import {SystemLogService} from '../../../services/system-log.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-system-log',
    templateUrl: './view-system-log.component.html',
    styleUrls: ['./view-system-log.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewSystemLogComponent implements OnInit {

    systemLog: SystemLog = new SystemLog;

    constructor(private route: ActivatedRoute, private location: Location,
                private systemLogService: SystemLogService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.systemLogService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.systemLog = response.data;
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
