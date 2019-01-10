


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AccountRecordService} from '../../services/account-record.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAccountRecordComponent} from './add-account-record/add-account-record.component';
import {EditAccountRecordComponent} from './edit-account-record/edit-account-record.component';
import {ViewAccountRecordComponent} from './view-account-record/view-account-record.component';
import {ListAccountRecordComponent} from './list-account-record/list-account-record.component';
import {FormAccountRecordComponent} from './form-account-record/form-account-record.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAccountRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAccountRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAccountRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAccountRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAccountRecordComponent,
        EditAccountRecordComponent,
        ViewAccountRecordComponent,
        ListAccountRecordComponent,
        FormAccountRecordComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AccountRecordService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountRecordModule {
}
