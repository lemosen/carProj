
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { Message } from '../../../models/original/message.model';
import { MessageService } from '../../../services/message.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-message',
    templateUrl: './add-message.component.html',
    styleUrls: ['./add-message.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddMessageComponent implements OnInit {

    submitting = false;

    message: Message;

    constructor(private router:Router,private messageService: MessageService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }

  /*  submitForm(formValue) {
        this.modalService.confirm({
            nzTitle  : '<i>提示！</i>',
            nzContent: '<b>确认要提交吗？</b>',
            nzOnOk   : () => this.confirmSub(formValue)
        });
    }
*/
  submitForm(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.messageService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/message/list']);
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
