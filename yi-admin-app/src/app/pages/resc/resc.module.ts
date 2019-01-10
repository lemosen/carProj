


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RescService} from '../../services/resc.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddRescComponent} from './add-resc/add-resc.component';
import {EditRescComponent} from './edit-resc/edit-resc.component';
import {ViewRescComponent} from './view-resc/view-resc.component';
import {ListRescComponent} from './list-resc/list-resc.component';
import {FormRescComponent} from './form-resc/form-resc.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'add', pathMatch: 'full'},
    {path: 'list', component: ListRescComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRescComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRescComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRescComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddRescComponent,
        EditRescComponent,
        ViewRescComponent,
        ListRescComponent,
        FormRescComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RescService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RescModule {
}
