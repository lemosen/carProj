


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RefundOrderService} from '../../services/refund-order.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddRefundOrderComponent} from './add-refund-order/add-refund-order.component';
import {EditRefundOrderComponent} from './edit-refund-order/edit-refund-order.component';
import {ViewRefundOrderComponent} from './view-refund-order/view-refund-order.component';
import {ListRefundOrderComponent} from './list-refund-order/list-refund-order.component';
import {FormRefundOrderComponent} from './form-refund-order/form-refund-order.component';
import {ComponentsModule} from '../components/components.module';
import {MemberService} from "../../services/member.service";
import {UserService} from "../../services/user.service";
import {MemberLevelService} from "../../services/member-level.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRefundOrderComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRefundOrderComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRefundOrderComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRefundOrderComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddRefundOrderComponent,
        EditRefundOrderComponent,
        ViewRefundOrderComponent,
        ListRefundOrderComponent,
        FormRefundOrderComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RefundOrderService,MemberService,UserService,MemberLevelService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RefundOrderModule {
}
