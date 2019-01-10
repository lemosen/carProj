import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ArticleCommdity} from '../../models/original/article-commdity.model';
import {ArticleCommdityService} from '../../../services/article-commdity.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-article-commdity',
    templateUrl: './view-article-commdity.component.html',
    styleUrls: ['./view-article-commdity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewArticleCommdityComponent implements OnInit {

    articleCommdity: ArticleCommdity = new ArticleCommdity;

    constructor(private route: ActivatedRoute, private location: Location,
                private articleCommdityService: ArticleCommdityService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.articleCommdityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.articleCommdity = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    goBack() {
        this.location.back();
    }

}
