import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {BasicConfig} from '../../models/original/basic-config.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {BasicConfigService} from '../../../services/basic-config.service';

@Component({
    selector: 'app-add-basic-config',
    templateUrl: './add-basic-config.component.html',
    styleUrls: ['./add-basic-config.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddBasicConfigComponent implements OnInit {

    submitted: boolean = false;

    basicConfig: BasicConfig;

    constructor(private router: Router, private basicConfigService: BasicConfigService, private dialogService: DialogsService) {
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
                this.basicConfigService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/basicConfig/list']);
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
