import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AreaService} from '../../services/area.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAreaComponent} from './add-area/add-area.component';
import {EditAreaComponent} from './edit-area/edit-area.component';
import {ViewAreaComponent} from './view-area/view-area.component';
import {ListAreaComponent} from './list-area/list-area.component';
import {FormAreaComponent} from './form-area/form-area.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAreaComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAreaComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAreaComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAreaComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAreaComponent,
        EditAreaComponent,
        ViewAreaComponent,
        ListAreaComponent,
        FormAreaComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AreaService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AreaModule {
}
