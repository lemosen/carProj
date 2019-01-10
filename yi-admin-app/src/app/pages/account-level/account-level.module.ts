import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AccountLevelService} from '../../services/account-level.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAccountLevelComponent} from './add-account-level/add-account-level.component';
import {EditAccountLevelComponent} from './edit-account-level/edit-account-level.component';
import {ViewAccountLevelComponent} from './view-account-level/view-account-level.component';
import {ListAccountLevelComponent} from './list-account-level/list-account-level.component';
import {FormAccountLevelComponent} from './form-account-level/form-account-level.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAccountLevelComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAccountLevelComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAccountLevelComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAccountLevelComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAccountLevelComponent,
        EditAccountLevelComponent,
        ViewAccountLevelComponent,
        ListAccountLevelComponent,
        FormAccountLevelComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AccountLevelService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountLevelModule {
}
