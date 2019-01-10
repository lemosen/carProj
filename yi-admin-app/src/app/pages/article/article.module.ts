


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ArticleService} from '../../services/article.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddArticleComponent} from './add-article/add-article.component';
import {EditArticleComponent} from './edit-article/edit-article.component';
import {ViewArticleComponent} from './view-article/view-article.component';
import {ListArticleComponent} from './list-article/list-article.component';
import {FormArticleComponent} from './form-article/form-article.component';
import {ComponentsModule} from '../components/components.module';
import {CommodityService} from "../../services/commodity.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListArticleComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddArticleComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditArticleComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewArticleComponent, data: {breadcrumb: '详情'}},
 /*   {path: 'choice', component: ChoiceArticleComponent, data: {breadcrumb: '选择商品'}},*/
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddArticleComponent,
        EditArticleComponent,
        ViewArticleComponent,
        ListArticleComponent,
        FormArticleComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ArticleService,CommodityService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleModule {
}
