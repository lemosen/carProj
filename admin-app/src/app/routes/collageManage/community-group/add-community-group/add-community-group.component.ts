
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { CommunityGroup } from '../../../models/original/community-group.model';
import { CommunityGroupService } from '../../../services/community-group.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-community-group',
    templateUrl: './add-community-group.component.html',
    styleUrls: ['./add-community-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddCommunityGroupComponent implements OnInit {

    submitting = false;

    communityGroup: CommunityGroup;

    constructor(private router:Router,private communityGroupService: CommunityGroupService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.communityGroupService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/community-group/list']);
                } else {
                    this.msgSrv.error('请求失败'+response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误'+error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }

}
