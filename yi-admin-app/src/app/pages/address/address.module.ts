import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AddressService} from '../../services/address.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAddressComponent} from './add-address/add-address.component';
import {EditAddressComponent} from './edit-address/edit-address.component';
import {ViewAddressComponent} from './view-address/view-address.component';
import {ListAddressComponent} from './list-address/list-address.component';
import {FormAddressComponent} from './form-address/form-address.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAddressComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAddressComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAddressComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAddressComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAddressComponent,
        EditAddressComponent,
        ViewAddressComponent,
        ListAddressComponent,
        FormAddressComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AddressService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AddressModule {
}
