import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {WithdrawService} from '../../services/withdraw.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddEssentialInformationComponent} from './add-essential-information/add-essential-information.component';
import {EditEssentialInformationComponent} from './edit-essential-information/edit-essential-information.component';
import {ViewEssentialInformationComponent} from './view-essential-information/view-essential-information.component';
import {ListEssentialInformationComponent} from './list-essential-information/list-essential-information.component';
import {FormEssentialInformationComponent} from './form-essential-information/form-essential-information.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListEssentialInformationComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddEssentialInformationComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditEssentialInformationComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewEssentialInformationComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddEssentialInformationComponent,
        EditEssentialInformationComponent,
        ViewEssentialInformationComponent,
        ListEssentialInformationComponent,
        FormEssentialInformationComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [WithdrawService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EssentialInformationModule {
}
