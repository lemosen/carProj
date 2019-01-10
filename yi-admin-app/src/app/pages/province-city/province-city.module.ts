import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ProvinceCityService} from '../../services/province-city.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddProvinceCityComponent} from './add-province-city/add-province-city.component';
import {EditProvinceCityComponent} from './edit-province-city/edit-province-city.component';
import {ViewProvinceCityComponent} from './view-province-city/view-province-city.component';
import {ListProvinceCityComponent} from './list-province-city/list-province-city.component';
import {FormProvinceCityComponent} from './form-province-city/form-province-city.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListProvinceCityComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddProvinceCityComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditProvinceCityComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewProvinceCityComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddProvinceCityComponent,
        EditProvinceCityComponent,
        ViewProvinceCityComponent,
        ListProvinceCityComponent,
        FormProvinceCityComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ProvinceCityService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProvinceCityModule {
}
