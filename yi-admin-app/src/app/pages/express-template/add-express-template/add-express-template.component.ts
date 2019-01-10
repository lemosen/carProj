
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {ExpressTemplate} from '../../models/original/express-template.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ExpressTemplateService} from '../../../services/express-template.service';

@Component({
    selector: 'app-add-express-template',
    templateUrl: './add-express-template.component.html',
    styleUrls: ['./add-express-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddExpressTemplateComponent implements OnInit {

    submitted: boolean = false;

    expressTemplate: ExpressTemplate;

    constructor(private router:Router,private expressTemplateService: ExpressTemplateService, private dialogService: DialogsService) {
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
                this.expressTemplateService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/express-template/list']);
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
