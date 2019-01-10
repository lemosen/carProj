import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';


import {ComponentsModule} from '../components/components.module';
import {HomeService} from "../../services/home.service";
import {ListPlatformDataComponent} from "./list-platform-data/list-platform-data.component";
import {SupplierService} from "../../services/supplier.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListPlatformDataComponent, data: {breadcrumb: '列表'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        ListPlatformDataComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [SupplierService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformDataModule {
}
