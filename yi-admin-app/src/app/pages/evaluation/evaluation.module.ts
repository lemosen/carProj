import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {EvaluationService} from '../../services/evaluation.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddEvaluationComponent} from './add-evaluation/add-evaluation.component';
import {EditEvaluationComponent} from './edit-evaluation/edit-evaluation.component';
import {ViewEvaluationComponent} from './view-evaluation/view-evaluation.component';
import {ListEvaluationComponent} from './list-evaluation/list-evaluation.component';
import {FormEvaluationComponent} from './form-evaluation/form-evaluation.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListEvaluationComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddEvaluationComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditEvaluationComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewEvaluationComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddEvaluationComponent,
        EditEvaluationComponent,
        ViewEvaluationComponent,
        ListEvaluationComponent,
        FormEvaluationComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [EvaluationService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EvaluationModule {
}
