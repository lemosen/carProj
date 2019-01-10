

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ArticleCommentService} from '../../../services/article-comment.service';
import {ArticleComment} from '../../models/original/article-comment.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-article-comment',
  templateUrl: './edit-article-comment.component.html',
  styleUrls: ['./edit-article-comment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditArticleCommentComponent implements OnInit {

  submitted: boolean = false;

  articleComment: ArticleComment;

  constructor(private route: ActivatedRoute,private router:Router,private articleCommentService: ArticleCommentService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.articleCommentService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.articleComment = response.data;
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
                this.articleCommentService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
