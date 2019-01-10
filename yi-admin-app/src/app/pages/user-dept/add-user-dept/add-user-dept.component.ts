import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {UserDept} from '../../models/original/user-dept.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {UserDeptService} from '../../../services/user-dept.service';

@Component({
    selector: 'app-add-user-dept',
    templateUrl: './add-user-dept.component.html',
    styleUrls: ['./add-user-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddUserDeptComponent implements OnInit {

    submitted: boolean = false;

    userDept: UserDept;

    constructor(private router: Router, private userDeptService: UserDeptService, private dialogService: DialogsService) {
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
                this.userDeptService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/userDept/list']);
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
