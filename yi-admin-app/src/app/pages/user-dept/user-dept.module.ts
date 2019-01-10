import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {UserDeptService} from '../../services/user-dept.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddUserDeptComponent} from './add-user-dept/add-user-dept.component';
import {EditUserDeptComponent} from './edit-user-dept/edit-user-dept.component';
import {ViewUserDeptComponent} from './view-user-dept/view-user-dept.component';
import {ListUserDeptComponent} from './list-user-dept/list-user-dept.component';
import {FormUserDeptComponent} from './form-user-dept/form-user-dept.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListUserDeptComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddUserDeptComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditUserDeptComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewUserDeptComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddUserDeptComponent,
        EditUserDeptComponent,
        ViewUserDeptComponent,
        ListUserDeptComponent,
        FormUserDeptComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [UserDeptService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserDeptModule {
}
