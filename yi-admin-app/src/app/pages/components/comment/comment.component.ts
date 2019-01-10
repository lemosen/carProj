import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild} from '@angular/core';
import {PageQuery} from "../../models/page-query.model";
import {CommentVo} from "../../models/domain/vo/comment-vo.model";
import {CommentService} from "./comment.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SUCCESS} from "../../models/constants.model";
import {DialogsService} from "../dialogs/dialogs.service";
import {UserSimple} from "../../models/domain/simple/user-simple.model";
import {Utils} from "../../../shared/utils/Utils";
import {ObjectUtils} from "../../../shared/utils/ObjectUtils";

@Component({
    selector: 'app-comment',
    templateUrl: './comment.component.html',
    styleUrls: ['./comment.component.scss'],
    providers: [CommentService]
})
export class CommentComponent implements OnInit, OnChanges {

    @Input() showRecordNum: boolean = false;

    @Input() objectPath: string = '';

    @ViewChild('commentAttachUpload') commentAttachUpload;

    @Output() onCommentSuccess: EventEmitter<boolean> = new EventEmitter();

    pageQuery: PageQuery = new PageQuery();

    comments: Array<CommentVo>;

    showAttachment: boolean = false;

    commonForm: FormGroup;

    submitted: boolean = false;

    replyToUser: UserSimple;

    callToUsers: Array<UserSimple>;

    formErrors = {
        content: [{
            name: 'required',
            msg: '内容不可为空'
        }]
    };

    constructor(private fb: FormBuilder, private commentService: CommentService, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.objectPath.currentValue !== undefined) {
            this.getDatas();
            this.commonForm.patchValue({objectPath: this.objectPath});
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            content: [null, Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(65535)])],
            objectPath: [],
            replyUserId: [],
            replyUserName: [],
            attachments: [],
            ats: [],
        });
    }

    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
        if (commonFormValid) {
            return this.commonForm.value;
        }
        return null;
    }

    onSubmit() {
        if (this.submitted) {
            return;
        }
        const formValue = this.submitCheck();
        if (formValue) {
            this.submitted = true;
            this.commentService.add(formValue).subscribe(response => {
                this.submitted = false;
                if (response.result == SUCCESS) {
                    Utils.toggleInArray(response.data, this.comments);
                    this.onCommentSuccess.emit(true);
                    this.reset();
                    this.dialogService.toast();
                } else {
                    this.dialogService.alert('请求失败', response.message);
                }
            }, error => {
                this.dialogService.alert('请求错误', error.message);
            });
        }
    }

    getDatas() {
        this.pageQuery.clearFilter();
        this.pageQuery.addFilter('objectPath', this.objectPath, 'contains');
        this.commentService.query(this.pageQuery).subscribe(response => {
            this.comments = response.content;
            this.pageQuery.covertResponses(response);
        });
    }

    commentPageChange(event) {
        this.pageQuery.page = event.page;
        this.getDatas();
    }

    reset() {
        this.commonForm.reset({content: null, objectPath: this.objectPath, replyUserId: 0, replyUserName: null, attachments: null, ats: null});
        this.callToUsers = null;
        this.replyToUser = null;
        this.showAttachment = false;
        this.commentAttachUpload.clearAttachments();
        this.getDatas();
    }

    replyUserSubscription(event) {
        this.replyToUser = event;
        this.commonForm.patchValue({replyUserId: event.userId, replyUserName: event.userName});
    }

    removeCommentItemSubscription(event) {
        this.commentService.removeById(event.commentId).subscribe(response => {
            if (response.result == SUCCESS) {
                Utils.removeItemInArray(event, this.comments);
                this.dialogService.toast();
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    callUsersChoose() {
        this.dialogService.userMultipleChoose(this.callToUsers, '@同事选择').then(result => {
            if (result) {
                this.callToUsers = result;
                this.commonForm.patchValue({ats: result});
            }
        });
    }

    attachmentResultsSubscription(event) {
        if (event) {
            this.commonForm.patchValue({attachments: event});
        }
    }

    removeCallUser(user) {
        if (this.callToUsers) {
            Utils.removeItemInArray(user, this.callToUsers);
        }
    }

}
