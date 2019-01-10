


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {DeptService} from '../../services/dept.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddDeptComponent} from './add-dept/add-dept.component';
import {EditDeptComponent} from './edit-dept/edit-dept.component';
import {ViewDeptComponent} from './view-dept/view-dept.component';
import {ListDeptComponent} from './list-dept/list-dept.component';
import {FormDeptComponent} from './form-dept/form-dept.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListDeptComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddDeptComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditDeptComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewDeptComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddDeptComponent,
        EditDeptComponent,
        ViewDeptComponent,
        ListDeptComponent,
        FormDeptComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [DeptService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeptModule {
}
