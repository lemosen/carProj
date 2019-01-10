


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RoleService} from '../../services/role.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddRoleComponent} from './add-role/add-role.component';
import {EditRoleComponent} from './edit-role/edit-role.component';
import {ViewRoleComponent} from './view-role/view-role.component';
import {ListRoleComponent} from './list-role/list-role.component';
import {FormRoleComponent} from './form-role/form-role.component';
import {ComponentsModule} from '../components/components.module';
import {ListRescComponent} from "./list-resc/list-resc.component";
import {RescService} from "../../services/resc.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRoleComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRoleComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRoleComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRoleComponent, data: {breadcrumb: '详情'}},
    {path: 'listResc/:objId', component: ListRescComponent, data: {breadcrumb: '权限'}},

];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddRoleComponent,
        EditRoleComponent,
        ViewRoleComponent,
        ListRoleComponent,
        FormRoleComponent,
        ListRescComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RoleService,RescService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoleModule {
}
