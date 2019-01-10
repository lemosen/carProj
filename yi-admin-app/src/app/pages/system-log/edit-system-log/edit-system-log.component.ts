import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SystemLogService} from '../../../services/system-log.service';
import {SystemLog} from '../../models/original/system-log.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-system-log',
    templateUrl: './edit-system-log.component.html',
    styleUrls: ['./edit-system-log.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditSystemLogComponent implements OnInit {

    submitted: boolean = false;

    systemLog: SystemLog;

    constructor(private route: ActivatedRoute, private router: Router, private systemLogService: SystemLogService, private dialogService: DialogsService) {
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
                this.systemLogService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
