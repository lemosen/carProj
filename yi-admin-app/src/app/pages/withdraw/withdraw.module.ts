import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {WithdrawService} from '../../services/withdraw.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddWithdrawComponent} from './add-withdraw/add-withdraw.component';
import {EditWithdrawComponent} from './edit-withdraw/edit-withdraw.component';
import {ViewWithdrawComponent} from './view-withdraw/view-withdraw.component';
import {ListWithdrawComponent} from './list-withdraw/list-withdraw.component';
import {FormWithdrawComponent} from './form-withdraw/form-withdraw.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListWithdrawComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddWithdrawComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditWithdrawComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewWithdrawComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddWithdrawComponent,
        EditWithdrawComponent,
        ViewWithdrawComponent,
        ListWithdrawComponent,
        FormWithdrawComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [WithdrawService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WithdrawModule {
}
