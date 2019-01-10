


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {MemberLevelService} from '../../services/member-level.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddMemberLevelComponent} from './add-member-level/add-member-level.component';
import {EditMemberLevelComponent} from './edit-member-level/edit-member-level.component';
import {ViewMemberLevelComponent} from './view-member-level/view-member-level.component';
import {ListMemberLevelComponent} from './list-member-level/list-member-level.component';
import {FormMemberLevelComponent} from './form-member-level/form-member-level.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListMemberLevelComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddMemberLevelComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditMemberLevelComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewMemberLevelComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddMemberLevelComponent,
        EditMemberLevelComponent,
        ViewMemberLevelComponent,
        ListMemberLevelComponent,
        FormMemberLevelComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [MemberLevelService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MemberLevelModule {
}
