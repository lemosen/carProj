import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RefundService} from '../../services/refund.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddRefundComponent} from './add-refund/add-refund.component';
import {EditRefundComponent} from './edit-refund/edit-refund.component';
import {ViewRefundComponent} from './view-refund/view-refund.component';
import {ListRefundComponent} from './list-refund/list-refund.component';
import {FormRefundComponent} from './form-refund/form-refund.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRefundComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRefundComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRefundComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRefundComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddRefundComponent,
        EditRefundComponent,
        ViewRefundComponent,
        ListRefundComponent,
        FormRefundComponent
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RefundService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RefundModule {
}
