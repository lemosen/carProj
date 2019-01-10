
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {AttributeValue} from '../../models/original/attribute-value.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AttributeValueService} from '../../../services/attribute-value.service';

@Component({
    selector: 'app-add-attribute-value',
    templateUrl: './add-attribute-value.component.html',
    styleUrls: ['./add-attribute-value.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAttributeValueComponent implements OnInit {

    submitted: boolean = false;

    attributeValue: AttributeValue;

    constructor(private router:Router,private attributeValueService: AttributeValueService, private dialogService: DialogsService) {
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
                this.attributeValueService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/attribute-value/list']);
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
