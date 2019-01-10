
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {AttributeName} from '../../models/original/attribute-name.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AttributeNameService} from '../../../services/attribute-name.service';

@Component({
    selector: 'app-add-attribute-name',
    templateUrl: './add-attribute-name.component.html',
    styleUrls: ['./add-attribute-name.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAttributeNameComponent implements OnInit {

    submitted: boolean = false;

    attributeName: AttributeName;

    constructor(private router:Router,private attributeNameService: AttributeNameService, private dialogService: DialogsService) {
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
                this.attributeNameService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/attribute-name/list']);
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
