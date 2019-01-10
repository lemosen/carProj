import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SaleOrderService} from '../../services/sale-order.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddOrderSetUpComponent} from './add-order-set-up/add-order-set-up.component';
import {EditOrderSetUpComponent} from './edit-order-set-up/edit-order-set-up.component';
import {ViewOrderSetUpComponent} from './view-order-set-up/view-order-set-up.component';
import {ListOrderSetUpComponent} from './list-order-set-up/list-order-set-up.component';
import {FormOrderSetUpComponent} from './form-order-set-up/form-order-set-up.component';
import {ComponentsModule} from '../components/components.module';
import {PipeModule} from "../../pipe/pipe.module";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListOrderSetUpComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddOrderSetUpComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditOrderSetUpComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewOrderSetUpComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        PipeModule.forRoot(),
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddOrderSetUpComponent,
        EditOrderSetUpComponent,
        FormOrderSetUpComponent,
        ListOrderSetUpComponent,
        ViewOrderSetUpComponent
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SaleOrderService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderSetUpModule {
}
