import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {TransactionRecordService} from '../../services/transaction-record.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddTransactionRecordComponent} from './add-transaction-record/add-transaction-record.component';
import {EditTransactionRecordComponent} from './edit-transaction-record/edit-transaction-record.component';
import {ViewTransactionRecordComponent} from './view-transaction-record/view-transaction-record.component';
import {ListTransactionRecordComponent} from './list-transaction-record/list-transaction-record.component';
import {FormTransactionRecordComponent} from './form-transaction-record/form-transaction-record.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListTransactionRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddTransactionRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditTransactionRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewTransactionRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddTransactionRecordComponent,
        EditTransactionRecordComponent,
        ViewTransactionRecordComponent,
        ListTransactionRecordComponent,
        FormTransactionRecordComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [TransactionRecordService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TransactionRecordModule {
}
