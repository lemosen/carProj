
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {MemberLevel} from '../../models/original/member-level.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {MemberLevelService} from '../../../services/member-level.service';

@Component({
    selector: 'app-add-member-level',
    templateUrl: './add-member-level.component.html',
    styleUrls: ['./add-member-level.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddMemberLevelComponent implements OnInit {

    submitted: boolean = false;

    memberLevel: MemberLevel;

    constructor(private router:Router,private memberLevelService: MemberLevelService, private dialogService: DialogsService) {
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
                this.memberLevelService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/member-level/list']);
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
