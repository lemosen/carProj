import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Logistics} from '../../models/original/logistics.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {LogisticsService} from '../../../services/logistics.service';

@Component({
    selector: 'app-add-logistics',
    templateUrl: './add-logistics.component.html',
    styleUrls: ['./add-logistics.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddLogisticsComponent implements OnInit {

    submitted: boolean = false;

    logistics: Logistics;

    constructor(private router: Router, private logisticsService: LogisticsService, private dialogService: DialogsService) {
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
                this.logisticsService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/logistics/list']);
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
