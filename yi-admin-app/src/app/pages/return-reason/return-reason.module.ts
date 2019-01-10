


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ReturnReasonService} from '../../services/return-reason.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddReturnReasonComponent} from './add-return-reason/add-return-reason.component';
import {EditReturnReasonComponent} from './edit-return-reason/edit-return-reason.component';
import {ViewReturnReasonComponent} from './view-return-reason/view-return-reason.component';
import {ListReturnReasonComponent} from './list-return-reason/list-return-reason.component';
import {FormReturnReasonComponent} from './form-return-reason/form-return-reason.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListReturnReasonComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddReturnReasonComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditReturnReasonComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewReturnReasonComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddReturnReasonComponent,
        EditReturnReasonComponent,
        ViewReturnReasonComponent,
        ListReturnReasonComponent,
        FormReturnReasonComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ReturnReasonService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReturnReasonModule {
}
