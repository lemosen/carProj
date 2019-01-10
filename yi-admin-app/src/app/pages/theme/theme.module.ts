


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ArticleService} from '../../services/article.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddThemeComponent} from './add-theme/add-theme.component';
import {EditThemeComponent} from './edit-theme/edit-theme.component';
import {ViewThemeComponent} from './view-theme/view-theme.component';
import {ListThemeComponent} from './list-theme/list-theme.component';
import {FormThemeComponent} from './form-theme/form-theme.component';
import {ComponentsModule} from '../components/components.module';
/*import {ChoiceArticleComponent} from "./choice-article/choice-article.component";*/
import {CommodityService} from "../../services/commodity.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListThemeComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddThemeComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditThemeComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewThemeComponent, data: {breadcrumb: '详情'}},
/*    {path: 'choice', component: ChoiceArticleComponent, data: {breadcrumb: '选择商品'}},*/
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddThemeComponent,
        EditThemeComponent,
        ViewThemeComponent,
        ListThemeComponent,
        FormThemeComponent,
      /*  ChoiceArticleComponent,*/
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ArticleService,CommodityService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ThemeModule {
}
