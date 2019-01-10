

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Article} from '../../models/original/article.model';
import {ArticleService} from '../../../services/article.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-article',
  templateUrl: './view-article.component.html',
  styleUrls: ['./view-article.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewArticleComponent implements OnInit {

    article: Article=new Article;

    constructor(private route: ActivatedRoute,private location: Location,
                private articleService: ArticleService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.articleService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.article = response.data;
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
