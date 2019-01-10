


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {OrderLogService} from '../../services/order-log.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddOrderLogComponent} from './add-order-log/add-order-log.component';
import {EditOrderLogComponent} from './edit-order-log/edit-order-log.component';
import {ViewOrderLogComponent} from './view-order-log/view-order-log.component';
import {ListOrderLogComponent} from './list-order-log/list-order-log.component';
import {FormOrderLogComponent} from './form-order-log/form-order-log.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListOrderLogComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddOrderLogComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditOrderLogComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewOrderLogComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddOrderLogComponent,
        EditOrderLogComponent,
        ViewOrderLogComponent,
        ListOrderLogComponent,
        FormOrderLogComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [OrderLogService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderLogModule {
}
