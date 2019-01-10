import {Component, OnInit, ViewChild} from '@angular/core';
import {Content, NavController} from "@ionic/angular";
import {ArticleProvider} from "../../services/article-service/article";
import {Article} from "../../domain/original/article.model";
import {PageQuery} from "../../util/page-query.model";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-love-life',
    templateUrl: './love-life.page.html',
    styleUrls: ['./love-life.page.scss'],
})
export class LoveLifePage implements OnInit {
    ArticleVo: Article[] = [];

    pageQuery: PageQuery = new PageQuery();
    @ViewChild("content") content: Content;

    constructor(public nativeProvider: NativeProvider, public articleProvider: ArticleProvider, public navCtrl: NavController) {

        let _this = this
        window.addEventListener('hashchange', function () {
            setTimeout(function () {
                var url = window.location.toString();
                var id = url.split("#")[2];
                if (id) {
                    if (document.getElementById(id)) {
                        var t = document.getElementById(id).offsetTop;
                        // document.getElementById('content').scrollTo({top: t});
                        _this.content.scrollByPoint(0,t,0);
                    }
                }
            }, 150)
        })
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.getData(this.pageQuery)
    }

    getData(page) {
        this.articleProvider.getArticleVo(page).then(data => {
            this.pageQuery.covertResponses(data);
            this.ArticleVo = data.content;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        })
    }

    goDetail(articleId) {
        window.location.href = './#/tabs/(loveLife:loveLife)#' + articleId;
        setTimeout(() => {
            this.navCtrl.navigateForward(["ArticleDetailPage", {"articleId": articleId}])
        }, 100)
    }

    doRefresh(refresher) {
        this.pageQuery.resetRequests();
        this.articleProvider.getArticleVo(this.pageQuery.requests).then(data => {
                this.ArticleVo = data.content;
                this.pageQuery.covertResponses(data);
                refresher.target.complete();
            },
            err => refresher.target.complete());
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.articleProvider.getArticleVo(this.pageQuery.requests).then(data => {
                    this.ArticleVo = this.ArticleVo.concat(data.content);
                    this.pageQuery.covertResponses(data);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }
}
