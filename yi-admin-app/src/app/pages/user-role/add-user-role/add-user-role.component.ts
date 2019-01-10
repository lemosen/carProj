
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {UserRole} from '../../models/original/user-role.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {UserRoleService} from '../../../services/user-role.service';

@Component({
    selector: 'app-add-user-role',
    templateUrl: './add-user-role.component.html',
    styleUrls: ['./add-user-role.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddUserRoleComponent implements OnInit {

    submitted: boolean = false;

    userRole: UserRole;

    constructor(private router:Router,private userRoleService: UserRoleService, private dialogService: DialogsService) {
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
                this.userRoleService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/userRole/list']);
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
