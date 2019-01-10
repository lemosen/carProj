


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {MemberService} from '../../services/member.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddMemberComponent} from './add-member/add-member.component';
import {EditMemberComponent} from './edit-member/edit-member.component';
import {ViewMemberComponent} from './view-member/view-member.component';
import {ListMemberComponent} from './list-member/list-member.component';
import {FormMemberComponent} from './form-member/form-member.component';
import {ComponentsModule} from '../components/components.module';
import {MemberLevelService} from "../../services/member-level.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListMemberComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddMemberComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditMemberComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewMemberComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddMemberComponent,
        EditMemberComponent,
        ViewMemberComponent,
        ListMemberComponent,
        FormMemberComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [MemberService,MemberLevelService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MemberModule {
}
