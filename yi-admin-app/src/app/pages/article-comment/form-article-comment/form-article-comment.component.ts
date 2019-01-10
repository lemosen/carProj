


import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ArticleComment} from '../../models/original/article-comment.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
  selector: 'app-form-article-comment',
  templateUrl: './form-article-comment.component.html',
  styleUrls: ['./form-article-comment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormArticleCommentComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() articleComment: ArticleComment =new ArticleComment();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);



    formErrors = {

        id:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        guid:[
        {
                         name: 'maxlength',
        msg: '最大32位长度',
        }
                    ],
        articleId:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大10位长度',
        }
                    ],
        commentator:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大127位长度',
        }
                    ],
        commentContent:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大255位长度',
        }
                    ],
        commentTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
        replyContent:[
        {
                         name: 'maxlength',
        msg: '最大255位长度',
        }
                    ],
        replyTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
        state:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        deleted:[
        {
                         name: 'required',
        msg: '不可为空',
        },
        {
                         name: 'maxlength',
        msg: '最大0位长度',
        }
                    ],
        delTime:[
        {
                         name: 'maxlength',
        msg: '最大19位长度',
        }
                    ],
    };

    constructor(private fb: FormBuilder,private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.articleComment !== undefined && value.articleComment.currentValue !== undefined) {
            this.setBuildFormValue(this.articleComment);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [
                    ],
            articleId: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(10)
                        ])
                    ],
            commentator: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(127)
                        ])
                    ],
            commentContent: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(255)
                        ])
                    ],
            commentTime: [
                    ],
            replyContent: [
                    ],
            replyTime: [
                    ],
            state: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
                        ])
                    ],
            deleted: [
                            null, Validators.compose([Validators.required,
                            Validators.maxLength(0)
                        ])
                    ],
            delTime: [
                    ],
        });
    }

    setBuildFormValue(articleComment: ArticleComment) {
        this.commonForm.setValue({
            guid:
            articleComment.guid
                ,
            articleId:
            articleComment.articleId
                ,
            commentator:
            articleComment.commentator
                ,
            commentContent:
            articleComment.commentContent
                ,
            commentTime:
            articleComment.commentTime
                ,
            replyContent:
            articleComment.replyContent
                ,
            replyTime:
            articleComment.replyTime
                ,
            state:
            articleComment.state
                ,
            deleted:
            articleComment.deleted
                ,
            delTime:
            articleComment.delTime
                ,
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
        const formValue = this.submitCheck();
 if (this.articleComment) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        console.log("commonForm value=" + JSON.stringify(formValue));
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack(){
        this.location.back();
    }

}
