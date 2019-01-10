


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {BrandService} from '../../services/brand.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddBrandComponent} from './add-brand/add-brand.component';
import {EditBrandComponent} from './edit-brand/edit-brand.component';
import {ViewBrandComponent} from './view-brand/view-brand.component';
import {ListBrandComponent} from './list-brand/list-brand.component';
import {FormBrandComponent} from './form-brand/form-brand.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListBrandComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddBrandComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditBrandComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewBrandComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddBrandComponent,
        EditBrandComponent,
        ViewBrandComponent,
        ListBrandComponent,
        FormBrandComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [BrandService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BrandModule {
}
