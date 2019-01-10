


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {BannerService} from '../../services/banner.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddBannerComponent} from './add-banner/add-banner.component';
import {EditBannerComponent} from './edit-banner/edit-banner.component';
import {ViewBannerComponent} from './view-banner/view-banner.component';
import {ListBannerComponent} from './list-banner/list-banner.component';
import {FormBannerComponent} from './form-banner/form-banner.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListBannerComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddBannerComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditBannerComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewBannerComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddBannerComponent,
        EditBannerComponent,
        ViewBannerComponent,
        ListBannerComponent,
        FormBannerComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [BannerService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BannerModule {
}
