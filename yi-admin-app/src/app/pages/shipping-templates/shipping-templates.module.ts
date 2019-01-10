import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RefundService} from '../../services/refund.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddShippingTemplatesComponent} from './add-shipping-templates/add-shipping-templates.component';
import {EditShippingTemplatesComponent} from './edit-shipping-templates/edit-shipping-templates.component';
import {ViewShippingTemplatesComponent} from './view-shipping-templates/view-shipping-templates.component';
import {ListShippingTemplatesComponent} from './list-shipping-templates/list-shipping-templates.component';
import {FormShippingTemplatesComponent} from './form-shipping-templates/form-shipping-templates.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListShippingTemplatesComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddShippingTemplatesComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditShippingTemplatesComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewShippingTemplatesComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddShippingTemplatesComponent,
        EditShippingTemplatesComponent,
        ViewShippingTemplatesComponent,
        ListShippingTemplatesComponent,
        FormShippingTemplatesComponent
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RefundService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShippingTemplatesModule {
}
