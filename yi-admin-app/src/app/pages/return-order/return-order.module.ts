


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ReturnOrderService} from '../../services/return-order.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddReturnOrderComponent} from './add-return-order/add-return-order.component';
import {EditReturnOrderComponent} from './edit-return-order/edit-return-order.component';
import {ViewReturnOrderComponent} from './view-return-order/view-return-order.component';
import {ListReturnOrderComponent} from './list-return-order/list-return-order.component';
import {FormReturnOrderComponent} from './form-return-order/form-return-order.component';
import {ComponentsModule} from '../components/components.module';
import {MemberService} from "../../services/member.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListReturnOrderComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddReturnOrderComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditReturnOrderComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewReturnOrderComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddReturnOrderComponent,
        EditReturnOrderComponent,
        ViewReturnOrderComponent,
        ListReturnOrderComponent,
        FormReturnOrderComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ReturnOrderService,MemberService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReturnOrderModule {
}
