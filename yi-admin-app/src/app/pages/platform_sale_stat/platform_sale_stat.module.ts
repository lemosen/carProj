import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

/*import {ProvinceCityService} from '../../services/province-city.service';*/

import {PlatformSaleStatService} from "../../services/platform-sale-stat.service";

import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {ListPlatformSaleStat} from './list-platform_sale_stat/list-platform_sale_stat.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListPlatformSaleStat, data: {breadcrumb: '列表'}},

];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [

        ListPlatformSaleStat
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [PlatformSaleStatService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlatformSaleStatModule {
}
