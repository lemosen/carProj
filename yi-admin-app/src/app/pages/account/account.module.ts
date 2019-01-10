


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AccountService} from '../../services/account.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAccountComponent} from "./add-account/add-account.component";
import {EditAccountComponent} from './edit-account/edit-account.component';
import {ViewAccountComponent} from './view-account/view-account.component';
import {ListAccountComponent} from './list-account/list-account.component';
import {FormAccountComponent} from './form-account/form-account.component';
import {ComponentsModule} from '../components/components.module';


export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAccountComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAccountComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAccountComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAccountComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAccountComponent,
        EditAccountComponent,
        ViewAccountComponent,
        ListAccountComponent,
        FormAccountComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AccountService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountModule {
}
