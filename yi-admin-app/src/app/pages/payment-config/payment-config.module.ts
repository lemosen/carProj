


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {PaymentConfigService} from '../../services/payment-config.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddPaymentConfigComponent} from './add-payment-config/add-payment-config.component';
import {EditPaymentConfigComponent} from './edit-payment-config/edit-payment-config.component';
import {ViewPaymentConfigComponent} from './view-payment-config/view-payment-config.component';
import {ListPaymentConfigComponent} from './list-payment-config/list-payment-config.component';
import {FormPaymentConfigComponent} from './form-payment-config/form-payment-config.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListPaymentConfigComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddPaymentConfigComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditPaymentConfigComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewPaymentConfigComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddPaymentConfigComponent,
        EditPaymentConfigComponent,
        ViewPaymentConfigComponent,
        ListPaymentConfigComponent,
        FormPaymentConfigComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [PaymentConfigService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaymentConfigModule {
}
