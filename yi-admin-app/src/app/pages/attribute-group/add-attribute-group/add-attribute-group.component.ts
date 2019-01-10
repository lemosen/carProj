
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {AttributeGroup} from '../../models/original/attribute-group.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AttributeGroupService} from '../../../services/attribute-group.service';

@Component({
    selector: 'app-add-attribute-group',
    templateUrl: './add-attribute-group.component.html',
    styleUrls: ['./add-attribute-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAttributeGroupComponent implements OnInit {

    submitted: boolean = false;

    attributeGroup: AttributeGroup;

    constructor(private router:Router,private attributeGroupService: AttributeGroupService, private dialogService: DialogsService) {
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
                this.attributeGroupService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/attribute-group/list']);
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
