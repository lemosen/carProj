


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AttributeGroupService} from '../../services/attribute-group.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAttributeGroupComponent} from './add-attribute-group/add-attribute-group.component';
import {EditAttributeGroupComponent} from './edit-attribute-group/edit-attribute-group.component';
import {ViewAttributeGroupComponent} from './view-attribute-group/view-attribute-group.component';
import {ListAttributeGroupComponent} from './list-attribute-group/list-attribute-group.component';
import {FormAttributeGroupComponent} from './form-attribute-group/form-attribute-group.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAttributeGroupComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAttributeGroupComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAttributeGroupComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAttributeGroupComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAttributeGroupComponent,
        EditAttributeGroupComponent,
        ViewAttributeGroupComponent,
        ListAttributeGroupComponent,
        FormAttributeGroupComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AttributeGroupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AttributeGroupModule {
}
