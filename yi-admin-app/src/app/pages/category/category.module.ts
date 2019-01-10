


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {CategoryService} from '../../services/category.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddCategoryComponent} from './add-category/add-category.component';
import {EditCategoryComponent} from './edit-category/edit-category.component';
import {ViewCategoryComponent} from './view-category/view-category.component';
import {ListCategoryComponent} from './list-category/list-category.component';
import {FormCategoryComponent} from './form-category/form-category.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCategoryComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCategoryComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCategoryComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCategoryComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddCategoryComponent,
        EditCategoryComponent,
        ViewCategoryComponent,
        ListCategoryComponent,
        FormCategoryComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [CategoryService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CategoryModule {
}
