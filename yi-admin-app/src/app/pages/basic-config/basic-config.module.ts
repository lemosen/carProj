import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {BasicConfigService} from '../../services/basic-config.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddBasicConfigComponent} from './add-basic-config/add-basic-config.component';
import {EditBasicConfigComponent} from './edit-basic-config/edit-basic-config.component';
import {ViewBasicConfigComponent} from './view-basic-config/view-basic-config.component';
import {ListBasicConfigComponent} from './list-basic-config/list-basic-config.component';
import {FormBasicConfigComponent} from './form-basic-config/form-basic-config.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListBasicConfigComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddBasicConfigComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditBasicConfigComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewBasicConfigComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddBasicConfigComponent,
        EditBasicConfigComponent,
        ViewBasicConfigComponent,
        ListBasicConfigComponent,
        FormBasicConfigComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [BasicConfigService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BasicConfigModule {
}
