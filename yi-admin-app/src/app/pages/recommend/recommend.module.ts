


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RecommendService} from '../../services/recommend.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddRecommendComponent} from './add-recommend/add-recommend.component';
import {EditRecommendComponent} from './edit-recommend/edit-recommend.component';
import {ViewRecommendComponent} from './view-recommend/view-recommend.component';
import {ListRecommendComponent} from './list-recommend/list-recommend.component';
import {FormRecommendComponent} from './form-recommend/form-recommend.component';
import {ComponentsModule} from '../components/components.module';
import {CommodityService} from "../../services/commodity.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRecommendComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRecommendComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRecommendComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRecommendComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddRecommendComponent,
        EditRecommendComponent,
        ViewRecommendComponent,
        ListRecommendComponent,
        FormRecommendComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RecommendService,CommodityService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecommendModule {
}
