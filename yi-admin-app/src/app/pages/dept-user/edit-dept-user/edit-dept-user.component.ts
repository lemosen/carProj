import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {DeptUserService} from '../../../services/dept-user.service';
import {DeptUser} from '../../models/original/dept-user.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-dept-user',
    templateUrl: './edit-dept-user.component.html',
    styleUrls: ['./edit-dept-user.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditDeptUserComponent implements OnInit {

    submitted: boolean = false;

    deptUser: DeptUser;

    constructor(private route: ActivatedRoute, private router: Router, private deptUserService: DeptUserService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.deptUserService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.deptUser = response.data;
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
                this.deptUserService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
