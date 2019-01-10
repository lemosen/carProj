import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AppendixTableService} from '../../services/appendix-table.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAppendixTableComponent} from './add-appendix-table/add-appendix-table.component';
import {EditAppendixTableComponent} from './edit-appendix-table/edit-appendix-table.component';
import {ViewAppendixTableComponent} from './view-appendix-table/view-appendix-table.component';
import {ListAppendixTableComponent} from './list-appendix-table/list-appendix-table.component';
import {FormAppendixTableComponent} from './form-appendix-table/form-appendix-table.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAppendixTableComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAppendixTableComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAppendixTableComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAppendixTableComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAppendixTableComponent,
        EditAppendixTableComponent,
        ViewAppendixTableComponent,
        ListAppendixTableComponent,
        FormAppendixTableComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AppendixTableService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppendixTableModule {
}
