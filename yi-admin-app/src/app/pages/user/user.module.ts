


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {UserService} from '../../services/user.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddUserComponent} from './add-user/add-user.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {ViewUserComponent} from './view-user/view-user.component';
import {ListUserComponent} from './list-user/list-user.component';
import {FormUserComponent} from './form-user/form-user.component';
import {ComponentsModule} from '../components/components.module';
import {DeptService} from "../../services/dept.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListUserComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddUserComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditUserComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewUserComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddUserComponent,
        EditUserComponent,
        ViewUserComponent,
        ListUserComponent,
        FormUserComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [UserService,DeptService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserModule {
}
