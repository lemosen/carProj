


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AttributeValueService} from '../../services/attribute-value.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAttributeValueComponent} from './add-attribute-value/add-attribute-value.component';
import {EditAttributeValueComponent} from './edit-attribute-value/edit-attribute-value.component';
import {ViewAttributeValueComponent} from './view-attribute-value/view-attribute-value.component';
import {ListAttributeValueComponent} from './list-attribute-value/list-attribute-value.component';
import {FormAttributeValueComponent} from './form-attribute-value/form-attribute-value.component';
import {ComponentsModule} from '../components/components.module';
import {AttributeNameService} from "../../services/attribute-name.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAttributeValueComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAttributeValueComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAttributeValueComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAttributeValueComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAttributeValueComponent,
        EditAttributeValueComponent,
        ViewAttributeValueComponent,
        ListAttributeValueComponent,
        FormAttributeValueComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AttributeValueService,AttributeNameService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AttributeValueModule {
}
