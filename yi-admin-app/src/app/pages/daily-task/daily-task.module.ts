


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {DailyTaskService} from '../../services/daily-task.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddDailyTaskComponent} from './add-daily-task/add-daily-task.component';
import {EditDailyTaskComponent} from './edit-daily-task/edit-daily-task.component';
import {ViewDailyTaskComponent} from './view-daily-task/view-daily-task.component';
import {ListDailyTaskComponent} from './list-daily-task/list-daily-task.component';
import {FormDailyTaskComponent} from './form-daily-task/form-daily-task.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListDailyTaskComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddDailyTaskComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditDailyTaskComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewDailyTaskComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddDailyTaskComponent,
        EditDailyTaskComponent,
        ViewDailyTaskComponent,
        ListDailyTaskComponent,
        FormDailyTaskComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [DailyTaskService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DailyTaskModule {
}
