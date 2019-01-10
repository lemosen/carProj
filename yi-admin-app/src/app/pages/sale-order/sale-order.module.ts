import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SaleOrderService} from '../../services/sale-order.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddSaleOrderComponent} from './add-sale-order/add-sale-order.component';
import {EditSaleOrderComponent} from './edit-sale-order/edit-sale-order.component';
import {ViewSaleOrderComponent} from './view-sale-order/view-sale-order.component';
import {ListSaleOrderComponent} from './list-sale-order/list-sale-order.component';
import {FormSaleOrderComponent} from './form-sale-order/form-sale-order.component';
import {ComponentsModule} from '../components/components.module';
import {EditPriceComponent} from "./edit-price/edit-price.component";
import {EditReceivingAddressComponent} from "./edit-receiving-address/edit-receiving-address.component";
import {MemberService} from "../../services/member.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSaleOrderComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSaleOrderComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSaleOrderComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSaleOrderComponent, data: {breadcrumb: '详情'}},
    {path: 'editPrice/:objId', component: EditPriceComponent, data: {breadcrumb: '修改价格'}},
    {path: 'editRecevingAddress/:objId', component: EditReceivingAddressComponent, data: {breadcrumb: '修改收货地址'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddSaleOrderComponent,
        EditSaleOrderComponent,
        ViewSaleOrderComponent,
        ListSaleOrderComponent,
        FormSaleOrderComponent,
        EditPriceComponent,
        EditReceivingAddressComponent
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SaleOrderService,MemberService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaleOrderModule {
}
