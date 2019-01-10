import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SupplierService} from '../../services/supplier.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';

import {ListSupplierWithdraw} from "./list-supplier-withdraw/list-supplier-withdraw.component";

import {ComponentsModule} from '../components/components.module';
import {SupplierWithdrawService} from "../../services/supplier-withdraw.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierWithdraw, data: {breadcrumb: '列表'}},

];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [

        ListSupplierWithdraw,

    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SupplierWithdrawService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SupplierWithdrawModule {

}
