import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {DeptUserService} from '../../services/dept-user.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddDeptUserComponent} from './add-dept-user/add-dept-user.component';
import {EditDeptUserComponent} from './edit-dept-user/edit-dept-user.component';
import {ViewDeptUserComponent} from './view-dept-user/view-dept-user.component';
import {ListDeptUserComponent} from './list-dept-user/list-dept-user.component';
import {FormDeptUserComponent} from './form-dept-user/form-dept-user.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListDeptUserComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddDeptUserComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditDeptUserComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewDeptUserComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddDeptUserComponent,
        EditDeptUserComponent,
        ViewDeptUserComponent,
        ListDeptUserComponent,
        FormDeptUserComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [DeptUserService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeptUserModule {
}
