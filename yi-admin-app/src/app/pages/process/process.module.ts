


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ProcessService} from '../../services/process.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddProcessComponent} from './add-process/add-process.component';
import {EditProcessComponent} from './edit-process/edit-process.component';
import {ViewProcessComponent} from './view-process/view-process.component';
import {ListProcessComponent} from './list-process/list-process.component';
import {FormProcessComponent} from './form-process/form-process.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListProcessComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddProcessComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditProcessComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewProcessComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddProcessComponent,
        EditProcessComponent,
        ViewProcessComponent,
        ListProcessComponent,
        FormProcessComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ProcessService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProcessModule {
}
