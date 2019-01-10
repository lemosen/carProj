import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ArticleCommdityService} from '../../services/article-commdity.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddArticleCommdityComponent} from './add-article-commdity/add-article-commdity.component';
import {EditArticleCommdityComponent} from './edit-article-commdity/edit-article-commdity.component';
import {ViewArticleCommdityComponent} from './view-article-commdity/view-article-commdity.component';
import {ListArticleCommdityComponent} from './list-article-commdity/list-article-commdity.component';
import {FormArticleCommdityComponent} from './form-article-commdity/form-article-commdity.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListArticleCommdityComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddArticleCommdityComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditArticleCommdityComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewArticleCommdityComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddArticleCommdityComponent,
        EditArticleCommdityComponent,
        ViewArticleCommdityComponent,
        ListArticleCommdityComponent,
        FormArticleCommdityComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ArticleCommdityService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArticleCommdityModule {
}
