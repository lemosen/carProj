import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommentVo} from "../../../models/domain/vo/comment-vo.model";
import {AppStorage} from "../../../configs/app-storage";
import {UserSimple} from "../../../models/domain/simple/user-simple.model";
import {DialogsService} from "../../dialogs/dialogs.service";

@Component({
    selector: 'app-comment-item',
    templateUrl: './comment-item.component.html',
    styleUrls: ['./comment-item.component.scss']
})
export class CommentItemComponent implements OnInit {

    @Input() comment: CommentVo;

    @Output() onReplyUser: EventEmitter<any> = new EventEmitter();

    @Output() onRemoveCommentItem: EventEmitter<any> = new EventEmitter();

    currentUser: UserSimple;

    constructor(private appStorage: AppStorage, private dialogService: DialogsService) {
        this.currentUser = this.appStorage.currentUser
    }

    ngOnInit() {
    }

    replyUser(userId, userName) {
        this.onReplyUser.emit({userId: userId, userName: userName});
    }

    removeCommentItem(item) {
        this.dialogService.confirm('提示', '确认要删除吗？').then(result => {
            if (result) {
                this.onRemoveCommentItem.emit(item);
            }
        });
    }
}
