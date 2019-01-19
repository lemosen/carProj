import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SUCCESS} from "../../models/constants.model";
import {CommentService} from "../../services/comment.service";
import {NzMessageService} from "ng-zorro-antd";
import {Router} from "@angular/router";
import {MemberService} from "../../services/member.service";

@Component({
  selector: 'nz-reply-comment',
  template: `
    <a style="color: #0090ff" *ngIf="showButton" (click)="showModal()">{{buttonName}}
      <ng-content></ng-content>
    </a>
    <button nz-button nzType="primary" type="submit" *ngIf="!showButton" (click)="showModal()">{{buttonName}}
      
    </button>
    <nz-modal *ngIf="comment" [(nzVisible)]="isVisible" [nzTitle]="title"
              (nzOnCancel)="handleCancel()" (nzOnOk)="handleOk()">
      <p >用户评论 : {{content}}</p>
      <textarea nz-input style="width: 100%;height: 150px" [(ngModel)]="replys"></textarea>
    </nz-modal>
    <nz-modal *ngIf="updateBalance" [(nzVisible)]="isVisible" [nzTitle]="title"
              (nzOnCancel)="handleCancel()" (nzOnOk)="handleOk()">
      <nz-form-label>赠送红包余额</nz-form-label><nz-input-number nz-input [nzMin]="0" [nzStep]="1" [nzPrecision]="2" 
                                                            style="width: 50%" [(ngModel)]="replys"></nz-input-number>
    </nz-modal>
  `,
  styles: []
})
export class CommentComponent {
  isVisible = false;
  @Input()
  title = ''
  @Input()
  content = ''
  @Input()
  buttonName = 'Show Modal'
  @Input()
  showButton = true
  @Input()
  comment = false
  @Input()
  updateBalance = false
  @Input()
  id=0;

  @Output()
  ok: EventEmitter<any> = new EventEmitter();

  replys="";

  constructor(public commentService:CommentService,public memberService: MemberService, public msg: NzMessageService) {
  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    if(this.comment){
      //回复
        if (this.replys=="") {
          this.msg.warning("回复内容不能为空")
          return
        }
        this.commentService.reply(this.id, this.replys).subscribe(response => {
          if (response.result == SUCCESS) {
            this.msg.success("回复成功");
            this.ok.emit("ok");
          } else {
            this.msg.error('请求失败', response.message);
          }
        }, error => {
          this.msg.error('请求错误', error.message);
        });
    }
    if(this.updateBalance){
      //回复
      if (this.replys=="") {
        this.msg.warning("赠送红包余额不能为空")
        return
      }
      this.memberService.updateBalance(this.id, this.replys).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("修改成功");
          this.ok.emit("ok");
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    }
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

}
