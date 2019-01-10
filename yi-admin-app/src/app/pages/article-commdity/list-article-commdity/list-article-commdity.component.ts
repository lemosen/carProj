import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ArticleCommdityService} from '../../../services/article-commdity.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-article-commdity',
    templateUrl: './list-article-commdity.component.html',
    styleUrls: ['./list-article-commdity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListArticleCommdityComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private articleCommdityService: ArticleCommdityService, public dialogService: DialogsService) {
        super(route, router, dialogService, articleCommdityService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
