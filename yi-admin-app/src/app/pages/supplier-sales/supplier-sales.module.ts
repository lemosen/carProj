import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ProvinceCityService} from '../../services/province-city.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {ListSupplierSales} from './list-supplier-sales/list-supplier-sales.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierSales, data: {breadcrumb: '列表'}},

];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        ListSupplierSales
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ProvinceCityService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SupplierSalesModule {
}
