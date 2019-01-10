/*

import { Component,TemplateRef } from '@angular/core';
import {CommentReplyModule} from "./comment-reply.module";


@Component({
    selector: 'comment-reply-root',
    templateUrl: './comment-reply.component.html',
    styleUrls: ['./comment-reply.component.css']
})
export class CommentReplyComponent {
    title = 'commentReply';
    public modalRef: BsModalRef;

    constructor(private modalService: BsModalService) {
    }

    showSecond(template: TemplateRef<any>) {//传入的是一个组件
        this.modalRef = this.modalService.show(template, {class: 'modal-lg'});//在这里通过BsModalService里面的show方法把它显示出来

    };
}*/
