
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {RoleResc} from '../../models/original/role-resc.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {RoleRescService} from '../../../services/role-resc.service';

@Component({
    selector: 'app-add-role-resc',
    templateUrl: './add-role-resc.component.html',
    styleUrls: ['./add-role-resc.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddRoleRescComponent implements OnInit {

    submitted: boolean = false;

    roleResc: RoleResc;

    constructor(private router:Router,private roleRescService: RoleRescService, private dialogService: DialogsService) {
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
                this.roleRescService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/roleResc/list']);
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
