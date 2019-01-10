


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SupplierCheckAccountService} from '../../services/supplier-check-account.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddSupplierCheckAccountComponent} from './add-supplier-check-account/add-supplier-check-account.component';
import {EditSupplierCheckAccountComponent} from './edit-supplier-check-account/edit-supplier-check-account.component';
import {ViewSupplierCheckAccountComponent} from './view-supplier-check-account/view-supplier-check-account.component';
import {ListSupplierCheckAccountComponent} from './list-supplier-check-account/list-supplier-check-account.component';
import {FormSupplierCheckAccountComponent} from './form-supplier-check-account/form-supplier-check-account.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierCheckAccountComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSupplierCheckAccountComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSupplierCheckAccountComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSupplierCheckAccountComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddSupplierCheckAccountComponent,
        EditSupplierCheckAccountComponent,
        ViewSupplierCheckAccountComponent,
        ListSupplierCheckAccountComponent,
        FormSupplierCheckAccountComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SupplierCheckAccountService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SupplierCheckAccountModule {
}
