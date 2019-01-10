import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {LogisticsService} from '../../services/logistics.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddLogisticsComponent} from './add-logistics/add-logistics.component';
import {EditLogisticsComponent} from './edit-logistics/edit-logistics.component';
import {ViewLogisticsComponent} from './view-logistics/view-logistics.component';
import {ListLogisticsComponent} from './list-logistics/list-logistics.component';
import {FormLogisticsComponent} from './form-logistics/form-logistics.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListLogisticsComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddLogisticsComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditLogisticsComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewLogisticsComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddLogisticsComponent,
        EditLogisticsComponent,
        ViewLogisticsComponent,
        ListLogisticsComponent,
        FormLogisticsComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [LogisticsService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LogisticsModule {
}
