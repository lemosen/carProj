
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Dept} from '../../models/original/dept.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {DeptService} from '../../../services/dept.service';

@Component({
    selector: 'app-add-dept',
    templateUrl: './add-dept.component.html',
    styleUrls: ['./add-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddDeptComponent implements OnInit {

    submitted: boolean = false;

    dept: Dept;

    constructor(private router:Router,private deptService: DeptService, private dialogService: DialogsService) {
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
                if (!formValue.obj.parent.id) {
                    formValue.obj.parent = null;
                }
                this.submitted = true;
                this.deptService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/dept/list']);
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
