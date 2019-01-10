
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {FreightTemplate} from '../../models/original/freight-template.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FreightTemplateService} from '../../../services/freight-template.service';

@Component({
    selector: 'app-add-freight-template',
    templateUrl: './add-freight-template.component.html',
    styleUrls: ['./add-freight-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddFreightTemplateComponent implements OnInit {

    submitted: boolean = false;

    freightTemplate: FreightTemplate;

    constructor(private router:Router,private freightTemplateService: FreightTemplateService, private dialogService: DialogsService) {
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
                this.freightTemplateService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/freight-template/list']);
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
