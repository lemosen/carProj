import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AccountLevelService} from '../../../services/account-level.service';
import {AccountLevel} from '../../models/original/account-level.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-account-level',
    templateUrl: './edit-account-level.component.html',
    styleUrls: ['./edit-account-level.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditAccountLevelComponent implements OnInit {

    submitted: boolean = false;

    accountLevel: AccountLevel;

    constructor(private route: ActivatedRoute, private router: Router, private accountLevelService: AccountLevelService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.accountLevelService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.accountLevel = response.data;
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
                formValue.obj.levelId=this.accountLevel.levelId
                this.accountLevelService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/account-level/list']);
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
