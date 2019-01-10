import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {DeptUser} from '../../models/original/dept-user.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {DeptUserService} from '../../../services/dept-user.service';

@Component({
    selector: 'app-add-dept-user',
    templateUrl: './add-dept-user.component.html',
    styleUrls: ['./add-dept-user.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddDeptUserComponent implements OnInit {

    submitted: boolean = false;

    deptUser: DeptUser;

    constructor(private router: Router, private deptUserService: DeptUserService, private dialogService: DialogsService) {
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
                this.deptUserService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/deptUser/list']);
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
