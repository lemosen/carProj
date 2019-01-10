


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ProductService} from '../../services/product.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddProductComponent} from './add-product/add-product.component';
import {EditProductComponent} from './edit-product/edit-product.component';
import {ViewProductComponent} from './view-product/view-product.component';
import {ListProductComponent} from './list-product/list-product.component';
import {FormProductComponent} from './form-product/form-product.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListProductComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddProductComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditProductComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewProductComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddProductComponent,
        EditProductComponent,
        ViewProductComponent,
        ListProductComponent,
        FormProductComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ProductService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProductModule {
}
