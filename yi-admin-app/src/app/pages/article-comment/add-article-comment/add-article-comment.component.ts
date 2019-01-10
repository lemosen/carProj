
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {ArticleComment} from '../../models/original/article-comment.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ArticleCommentService} from '../../../services/article-comment.service';

@Component({
    selector: 'app-add-article-comment',
    templateUrl: './add-article-comment.component.html',
    styleUrls: ['./add-article-comment.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddArticleCommentComponent implements OnInit {

    submitted: boolean = false;

    articleComment: ArticleComment;

    constructor(private router:Router,private articleCommentService: ArticleCommentService, private dialogService: DialogsService) {
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
                this.articleCommentService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/articleComment/list']);
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
