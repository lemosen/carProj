


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RecommendProductService} from '../../services/recommend-product.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddRecommendProductComponent} from './add-recommend-product/add-recommend-product.component';
import {EditRecommendProductComponent} from './edit-recommend-product/edit-recommend-product.component';
import {ViewRecommendProductComponent} from './view-recommend-product/view-recommend-product.component';
import {ListRecommendProductComponent} from './list-recommend-product/list-recommend-product.component';
import {FormRecommendProductComponent} from './form-recommend-product/form-recommend-product.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRecommendProductComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRecommendProductComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRecommendProductComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRecommendProductComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddRecommendProductComponent,
        EditRecommendProductComponent,
        ViewRecommendProductComponent,
        ListRecommendProductComponent,
        FormRecommendProductComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RecommendProductService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecommendProductModule {
}
