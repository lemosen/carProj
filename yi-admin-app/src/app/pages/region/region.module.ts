


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RegionService} from '../../services/region.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddRegionComponent} from './add-region/add-region.component';
import {EditRegionComponent} from './edit-region/edit-region.component';
import {ViewRegionComponent} from './view-region/view-region.component';
import {ListRegionComponent} from './list-region/list-region.component';
import {FormRegionComponent} from './form-region/form-region.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRegionComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRegionComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRegionComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRegionComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddRegionComponent,
        EditRegionComponent,
        ViewRegionComponent,
        ListRegionComponent,
        FormRegionComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RegionService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RegionModule {
}
