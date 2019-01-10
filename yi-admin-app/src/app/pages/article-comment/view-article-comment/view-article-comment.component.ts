

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ArticleComment} from '../../models/original/article-comment.model';
import {ArticleCommentService} from '../../../services/article-comment.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-article-comment',
  templateUrl: './view-article-comment.component.html',
  styleUrls: ['./view-article-comment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewArticleCommentComponent implements OnInit {

    articleComment: ArticleComment=new ArticleComment;

    constructor(private route: ActivatedRoute,private location: Location,
                private articleCommentService: ArticleCommentService, private dialogService: DialogsService) { }

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

    goBack(){
        this.location.back();
    }

}
