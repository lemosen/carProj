


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ShippingAddressService} from '../../services/shipping-address.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddShippingAddressComponent} from './add-shipping-address/add-shipping-address.component';
import {EditShippingAddressComponent} from './edit-shipping-address/edit-shipping-address.component';
import {ViewShippingAddressComponent} from './view-shipping-address/view-shipping-address.component';
import {ListShippingAddressComponent} from './list-shipping-address/list-shipping-address.component';
import {FormShippingAddressComponent} from './form-shipping-address/form-shipping-address.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListShippingAddressComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddShippingAddressComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditShippingAddressComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewShippingAddressComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddShippingAddressComponent,
        EditShippingAddressComponent,
        ViewShippingAddressComponent,
        ListShippingAddressComponent,
        FormShippingAddressComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ShippingAddressService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShippingAddressModule {
}
