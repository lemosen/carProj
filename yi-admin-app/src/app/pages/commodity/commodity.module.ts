


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {CommodityService} from '../../services/commodity.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddCommodityComponent} from './add-commodity/add-commodity.component';
import {EditCommodityComponent} from './edit-commodity/edit-commodity.component';
import {ViewCommodityComponent} from './view-commodity/view-commodity.component';
import {ListCommodityComponent} from './list-commodity/list-commodity.component';
import {FormCommodityComponent} from './form-commodity/form-commodity.component';
import {ComponentsModule} from '../components/components.module';
import {SupplierService} from "../../services/supplier.service";
import {FreightTemplateService} from "../../services/freight-template.service";
import {CategoryService} from "../../services/category.service";
import {RegionService} from "../../services/region.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCommodityComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCommodityComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCommodityComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCommodityComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddCommodityComponent,
        EditCommodityComponent,
        ViewCommodityComponent,
        ListCommodityComponent,
        FormCommodityComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [CommodityService,SupplierService,FreightTemplateService,CategoryService,RegionService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommodityModule {
}
