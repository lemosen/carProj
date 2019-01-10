import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ArticleService} from '../../../services/article.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-article',
    templateUrl: './list-article.component.html',
    styleUrls: ['./list-article.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListArticleComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private articleService: ArticleService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, articleService);
        this.buildForm();
    }

    menus = [
        {name: "全部专题", code: 0, value: ""},
        {name: "显示中", code: 1, value: 0},
        {name: "已下架", code: 2, value: 1},
    ];

    selectMenu: number = 0

    onItemClick(i) {
        this.pageQuery.clearFilter();
        let elementsByClassName: any = document.getElementsByClassName("filter");
        for (let elementsByClassNameKey in elementsByClassName) {
            elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
        }
        this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('state');
        this.getDatas();
        this.selectMenu = i;
    }

    ngOnInit() {
        this.getDatas();
    }

    getDate(event) {
        //todo 赋值操作
        console.log(event);
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            title: [null],
            subtitle: [null],
            author: [null],
            content: [null],
            imgPath: [null],
            url: [null],
            state: [null],
            createTime: [null],
            deleted: [null],
            delTime: [null],
        });
    }

    searchData() {

        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            id: null,
            guid: null,
            title: null,
            subtitle: null,
            author: null,
            content: null,
            imgPath: null,
            url: null,
            state: null,
            createTime: null,
            deleted: null,
            delTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.title != null) {
            pageQuery.addOnlyFilter('title', searchObj.title, 'contains');
        }
        return pageQuery;
    }



    onTheShelf(articleId){
        this.dialogService.confirm('提示', '确认上架吗？').then((result) => {
            if (result) {
                this.articleService.upAndDown(articleId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        /*this.router.navigate(['/pages/member/list']);*/
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        }, () => {
        });
    }



    lowerFrame(articleId){
        this.dialogService.confirm('提示', '确认下架吗？').then((result) => {
            if (result) {
                this.articleService.upAndDown(articleId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        /*this.router.navigate(['/pages/member/list']);*/
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        }, () => {
        });
    }


}
