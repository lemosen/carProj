


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RoleRescService} from '../../services/role-resc.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddRoleRescComponent} from './add-role-resc/add-role-resc.component';
import {EditRoleRescComponent} from './edit-role-resc/edit-role-resc.component';
import {ViewRoleRescComponent} from './view-role-resc/view-role-resc.component';
import {ListRoleRescComponent} from './list-role-resc/list-role-resc.component';
import {FormRoleRescComponent} from './form-role-resc/form-role-resc.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRoleRescComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRoleRescComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRoleRescComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRoleRescComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddRoleRescComponent,
        EditRoleRescComponent,
        ViewRoleRescComponent,
        ListRoleRescComponent,
        FormRoleRescComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RoleRescService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoleRescModule {
}
