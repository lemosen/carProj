


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AttributeNameService} from '../../services/attribute-name.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAttributeNameComponent} from './add-attribute-name/add-attribute-name.component';
import {EditAttributeNameComponent} from './edit-attribute-name/edit-attribute-name.component';
import {ViewAttributeNameComponent} from './view-attribute-name/view-attribute-name.component';
import {ListAttributeNameComponent} from './list-attribute-name/list-attribute-name.component';
import {FormAttributeNameComponent} from './form-attribute-name/form-attribute-name.component';
import {ComponentsModule} from '../components/components.module';
import {AttributeGroupService} from "../../services/attribute-group.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAttributeNameComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAttributeNameComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAttributeNameComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAttributeNameComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAttributeNameComponent,
        EditAttributeNameComponent,
        ViewAttributeNameComponent,
        ListAttributeNameComponent,
        FormAttributeNameComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AttributeNameService,AttributeGroupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AttributeNameModule {
}
