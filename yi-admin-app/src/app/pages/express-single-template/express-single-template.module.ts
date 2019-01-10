import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RefundService} from '../../services/refund.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddExpressSingleTemplateComponent} from './add-express-single-template/add-express-single-template.component';
import {EditExpressSingleTemplateComponent} from './edit-express-single-template/edit-express-single-template.component';
import {ViewExpressSingleTemplateComponent} from './view-express-single-template/view-express-single-template.component';
import {ListExpressSingleTemplateComponent} from './list-express-single-template/list-express-single-template.component';
import {FormExpressSingleTemplateComponent} from './form-express-single-template/form-express-single-template.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListExpressSingleTemplateComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddExpressSingleTemplateComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditExpressSingleTemplateComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewExpressSingleTemplateComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddExpressSingleTemplateComponent,
        EditExpressSingleTemplateComponent,
        ViewExpressSingleTemplateComponent,
        ListExpressSingleTemplateComponent,
        FormExpressSingleTemplateComponent
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [RefundService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExpressSingleTemplateModule {
}
