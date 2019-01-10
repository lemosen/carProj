


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {FreightTemplateService} from '../../services/freight-template.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddFreightTemplateComponent} from './add-freight-template/add-freight-template.component';
import {EditFreightTemplateComponent} from './edit-freight-template/edit-freight-template.component';
import {ViewFreightTemplateComponent} from './view-freight-template/view-freight-template.component';
import {ListFreightTemplateComponent} from './list-freight-template/list-freight-template.component';
import {FormFreightTemplateComponent} from './form-freight-template/form-freight-template.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListFreightTemplateComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddFreightTemplateComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditFreightTemplateComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewFreightTemplateComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddFreightTemplateComponent,
        EditFreightTemplateComponent,
        ViewFreightTemplateComponent,
        ListFreightTemplateComponent,
        FormFreightTemplateComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [FreightTemplateService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FreightTemplateModule {
}
