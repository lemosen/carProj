


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SupplierService} from '../../services/supplier.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddSupplierComponent} from './add-supplier/add-supplier.component';
import {EditSupplierComponent} from './edit-supplier/edit-supplier.component';
import {ViewSupplierComponent} from './view-supplier/view-supplier.component';
import {ListSupplierComponent} from './list-supplier/list-supplier.component';
import {FormSupplierComponent} from './form-supplier/form-supplier.component';
import {ComponentsModule} from '../components/components.module';
import {ListSalesListComponent} from "./list-sales-list/list-sales-list.component";
import {UserService} from "../../services/user.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSupplierComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSupplierComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSupplierComponent, data: {breadcrumb: '详情'}},
    {path: 'salesList/:objId', component: ListSalesListComponent, data: {breadcrumb: '销售列表'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddSupplierComponent,
        EditSupplierComponent,
        ViewSupplierComponent,
        ListSupplierComponent,
        FormSupplierComponent,
        ListSalesListComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SupplierService,UserService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SupplierModule{
}
