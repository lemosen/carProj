import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {BasicConfigService} from '../../../services/basic-config.service';
import {BasicConfig} from '../../models/original/basic-config.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-basic-config',
    templateUrl: './edit-basic-config.component.html',
    styleUrls: ['./edit-basic-config.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditBasicConfigComponent implements OnInit {

    submitted: boolean = false;

    basicConfig: BasicConfig;

    constructor(private route: ActivatedRoute, private router: Router, private basicConfigService: BasicConfigService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.basicConfigService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.basicConfig = response.data;
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
                this.basicConfigService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
