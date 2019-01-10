


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AttachmentService} from '../../services/attachment.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAttachmentComponent} from './add-attachment/add-attachment.component';
import {EditAttachmentComponent} from './edit-attachment/edit-attachment.component';
import {ViewAttachmentComponent} from './view-attachment/view-attachment.component';
import {ListAttachmentComponent} from './list-attachment/list-attachment.component';
import {FormAttachmentComponent} from './form-attachment/form-attachment.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAttachmentComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAttachmentComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAttachmentComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAttachmentComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAttachmentComponent,
        EditAttachmentComponent,
        ViewAttachmentComponent,
        ListAttachmentComponent,
        FormAttachmentComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AttachmentService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AttachmentModule {
}
