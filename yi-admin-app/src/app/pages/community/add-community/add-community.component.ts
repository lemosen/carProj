
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Community} from '../../models/original/community.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {CommunityService} from '../../../services/community.service';
import {MemberService} from "../../../services/member.service";
import {PageQuery} from "../../models/page-query.model";

@Component({
    selector: 'app-add-community',
    templateUrl: './add-community.component.html',
    styleUrls: ['./add-community.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddCommunityComponent implements OnInit {

    submitted: boolean = false;

    community: Community;

    constructor(private router:Router,private communityService: CommunityService,public memberService:MemberService,private dialogService: DialogsService) {
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
         /*   formValue.id=formValue.member.id;*/
            return;

        }
        this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                this.submitted = true;
                this.communityService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/community/list']);
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
