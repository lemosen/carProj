


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {ExpressTemplateService} from '../../services/express-template.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddExpressTemplateComponent} from './add-express-template/add-express-template.component';
import {EditExpressTemplateComponent} from './edit-express-template/edit-express-template.component';
import {ViewExpressTemplateComponent} from './view-express-template/view-express-template.component';
import {ListExpressTemplateComponent} from './list-express-template/list-express-template.component';
import {FormExpressTemplateComponent} from './form-express-template/form-express-template.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListExpressTemplateComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddExpressTemplateComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditExpressTemplateComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewExpressTemplateComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddExpressTemplateComponent,
        EditExpressTemplateComponent,
        ViewExpressTemplateComponent,
        ListExpressTemplateComponent,
        FormExpressTemplateComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [ExpressTemplateService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExpressTemplateModule {
}
