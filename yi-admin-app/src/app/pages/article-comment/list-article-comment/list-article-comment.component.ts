


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ArticleCommentService} from '../../../services/article-comment.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-article-comment',
    templateUrl: './list-article-comment.component.html',
    styleUrls: ['./list-article-comment.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListArticleCommentComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private articleCommentService: ArticleCommentService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, articleCommentService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        articleId:[null],
        commentator:[null],
        commentContent:[null],
        commentTime:[null],
        replyContent:[null],
        replyTime:[null],
        state:[null],
        deleted:[null],
        delTime:[null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
        id:null,
        guid:null,
        articleId:null,
        commentator:null,
        commentContent:null,
        commentTime:null,
        replyContent:null,
        replyTime:null,
        state:null,
        deleted:null,
        delTime:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.provinceId) {
     pageQuery.addOnlyFilter('id', searchObj.id, 'eq');
     pageQuery.addOnlyFilter('guid', searchObj.guid, 'eq');
     pageQuery.addOnlyFilter('articleId', searchObj.articleId, 'eq');
     pageQuery.addOnlyFilter('commentator', searchObj.commentator, 'eq');
     pageQuery.addOnlyFilter('commentContent', searchObj.commentContent, 'eq');
     pageQuery.addOnlyFilter('commentTime', searchObj.commentTime, 'eq');
     pageQuery.addOnlyFilter('replyContent', searchObj.replyContent, 'eq');
     pageQuery.addOnlyFilter('replyTime', searchObj.replyTime, 'eq');
     pageQuery.addOnlyFilter('state', searchObj.state, 'eq');
     pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
     pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
