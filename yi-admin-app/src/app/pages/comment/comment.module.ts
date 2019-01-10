


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddCommentComponent} from './add-comment/add-comment.component';
import {EditCommentComponent} from './edit-comment/edit-comment.component';
import {ViewCommentComponent} from './view-comment/view-comment.component';
import {ListCommentComponent} from './list-comment/list-comment.component';
import {FormCommentComponent} from './form-comment/form-comment.component';
import {ComponentsModule} from '../components/components.module';
import {CommentService} from "../components/comment/comment.service";
import {MemberService} from "../../services/member.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCommentComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCommentComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCommentComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCommentComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddCommentComponent,
        EditCommentComponent,
        ViewCommentComponent,
        ListCommentComponent,
        FormCommentComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [CommentService,MemberService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommentModule {
}
