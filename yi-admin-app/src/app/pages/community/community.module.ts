


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {CommunityService} from '../../services/community.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddCommunityComponent} from './add-community/add-community.component';
import {EditCommunityComponent} from './edit-community/edit-community.component';
import {ViewCommunityComponent} from './view-community/view-community.component';
import {ListCommunityComponent} from './list-community/list-community.component';
import {FormCommunityComponent} from './form-community/form-community.component';
import {ComponentsModule} from '../components/components.module';
import {ListPerformanceListComponent} from "./list-performance-list/list-performance-list.component";
import {MemberService} from "../../services/member.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCommunityComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCommunityComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCommunityComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCommunityComponent, data: {breadcrumb: '详情'}},
    {path: 'performanceList', component: ListPerformanceListComponent, data: {breadcrumb: '小区业绩列表'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddCommunityComponent,
        EditCommunityComponent,
        ViewCommunityComponent,
        ListCommunityComponent,
        FormCommunityComponent,
        ListPerformanceListComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [CommunityService,MemberService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommunityModule {
}
