


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AdvertisementService} from '../../services/advertisement.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAdvertisementComponent} from './add-advertisement/add-advertisement.component';
import {EditAdvertisementComponent} from './edit-advertisement/edit-advertisement.component';
import {ViewAdvertisementComponent} from './view-advertisement/view-advertisement.component';
import {ListAdvertisementComponent} from './list-advertisement/list-advertisement.component';
import {FormAdvertisementComponent} from './form-advertisement/form-advertisement.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAdvertisementComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAdvertisementComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAdvertisementComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAdvertisementComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAdvertisementComponent,
        EditAdvertisementComponent,
        ViewAdvertisementComponent,
        ListAdvertisementComponent,
        FormAdvertisementComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AdvertisementService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertisementModule {
}
