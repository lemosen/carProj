


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ConsumeRecordService} from '../../services/consume-record.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddConsumeRecordComponent} from './add-consume-record/add-consume-record.component';
import {EditConsumeRecordComponent} from './edit-consume-record/edit-consume-record.component';
import {ViewConsumeRecordComponent} from './view-consume-record/view-consume-record.component';
import {ListConsumeRecordComponent} from './list-consume-record/list-consume-record.component';
import {FormConsumeRecordComponent} from './form-consume-record/form-consume-record.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListConsumeRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddConsumeRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditConsumeRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewConsumeRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddConsumeRecordComponent,
        EditConsumeRecordComponent,
        ViewConsumeRecordComponent,
        ListConsumeRecordComponent,
        FormConsumeRecordComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ConsumeRecordService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConsumeRecordModule {
}
