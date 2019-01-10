


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {OrderSettingService} from '../../services/order-setting.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddOrderSettingComponent} from './add-order-setting/add-order-setting.component';
import {EditOrderSettingComponent} from './edit-order-setting/edit-order-setting.component';
import {ViewOrderSettingComponent} from './view-order-setting/view-order-setting.component';
import {ListOrderSettingComponent} from './list-order-setting/list-order-setting.component';
import {FormOrderSettingComponent} from './form-order-setting/form-order-setting.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListOrderSettingComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddOrderSettingComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditOrderSettingComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewOrderSettingComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddOrderSettingComponent,
        EditOrderSettingComponent,
        ViewOrderSettingComponent,
        ListOrderSettingComponent,
        FormOrderSettingComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [OrderSettingService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderSettingModule {
}
