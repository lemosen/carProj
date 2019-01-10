import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';

import {ListSupplierRecordComponent} from './list-supplier-record/list-supplier-record.component';

import {ComponentsModule} from '../components/components.module';
import {SupplierWithdrawService} from "../../services/supplier-withdraw.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierRecordComponent, data: {breadcrumb: '列表'}},

];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [

        ListSupplierRecordComponent,

    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SupplierWithdrawService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SupplierRecordModule {

}
