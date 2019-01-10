import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SupplierService} from '../../services/supplier.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';

import {ListSupplierComponent} from './list-supplier-profile/list-supplier.component';

import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierComponent, data: {breadcrumb: '列表'}},

];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [

        ListSupplierComponent,

    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SupplierService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SupplierProfileModule {
}
