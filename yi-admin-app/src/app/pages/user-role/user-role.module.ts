


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {UserRoleService} from '../../services/user-role.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddUserRoleComponent} from './add-user-role/add-user-role.component';
import {EditUserRoleComponent} from './edit-user-role/edit-user-role.component';
import {ViewUserRoleComponent} from './view-user-role/view-user-role.component';
import {ListUserRoleComponent} from './list-user-role/list-user-role.component';
import {FormUserRoleComponent} from './form-user-role/form-user-role.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListUserRoleComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddUserRoleComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditUserRoleComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewUserRoleComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddUserRoleComponent,
        EditUserRoleComponent,
        ViewUserRoleComponent,
        ListUserRoleComponent,
        FormUserRoleComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [UserRoleService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserRoleModule {
}
