


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ArticleCommentService} from '../../services/article-comment.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddArticleCommentComponent} from './add-article-comment/add-article-comment.component';
import {EditArticleCommentComponent} from './edit-article-comment/edit-article-comment.component';
import {ViewArticleCommentComponent} from './view-article-comment/view-article-comment.component';
import {ListArticleCommentComponent} from './list-article-comment/list-article-comment.component';
import {FormArticleCommentComponent} from './form-article-comment/form-article-comment.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListArticleCommentComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddArticleCommentComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditArticleCommentComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewArticleCommentComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddArticleCommentComponent,
        EditArticleCommentComponent,
        ViewArticleCommentComponent,
        ListArticleCommentComponent,
        FormArticleCommentComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ArticleCommentService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleCommentModule {
}
