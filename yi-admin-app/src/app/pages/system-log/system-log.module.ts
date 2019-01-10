import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SystemLogService} from '../../services/system-log.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddSystemLogComponent} from './add-system-log/add-system-log.component';
import {EditSystemLogComponent} from './edit-system-log/edit-system-log.component';
import {ViewSystemLogComponent} from './view-system-log/view-system-log.component';
import {ListSystemLogComponent} from './list-system-log/list-system-log.component';
import {FormSystemLogComponent} from './form-system-log/form-system-log.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSystemLogComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSystemLogComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSystemLogComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSystemLogComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddSystemLogComponent,
        EditSystemLogComponent,
        ViewSystemLogComponent,
        ListSystemLogComponent,
        FormSystemLogComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SystemLogService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SystemLogModule {
}
