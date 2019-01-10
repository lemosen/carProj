import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {DialogsService} from "./dialogs.service";
import {UserSingleDialogComponent} from './user-dialog/user-single-dialog/user-single-dialog.component';
import {UserMultipleDialogComponent} from './user-dialog/user-multiple-dialog/user-multiple-dialog.component';
import {DeptMultipleDialogComponent} from './dept-dialog/dept-multiple-dialog/dept-multiple-dialog.component';
import {DeptSingleDialogComponent} from './dept-dialog/dept-single-dialog/dept-single-dialog.component';

import {ConfirmDialogComponent} from './confirm-dialog/confirm-dialog.component';
import {AlertDialogComponent} from './alert-dialog/alert-dialog.component';
import {SearchModule} from "../search/search.module";
import {SelectedItemsComponent} from './selected-items/selected-items.component';
import {TagsModule} from "../tags/tags.module";
import {DeptBreadcrumbModule} from "./breadcrumb/dept-breadcrumb/dept-breadcrumb.module";
import {ResultLevelItemsComponent} from './result-items/result-level-items/result-level-items.component';
import {ResultNoLevelItemsComponent} from './result-items/result-no-level-items/result-no-level-items.component';
import {StateDialogComponent} from './state-dialog/state-dialog.component';
import {FormErrorModule} from "../form-error/form-error.module";
import {PromptDialogComponent} from './prompt-dialog/prompt-dialog.component';

@NgModule({
    imports: [
        SharedModule,
        SearchModule,
        DeptBreadcrumbModule,
        TagsModule,
        FormErrorModule,
    ],
    declarations: [
        UserSingleDialogComponent,
        UserMultipleDialogComponent,
        DeptMultipleDialogComponent,
        DeptSingleDialogComponent,
        ConfirmDialogComponent,
        AlertDialogComponent,
        SelectedItemsComponent,

        ResultLevelItemsComponent,
        ResultNoLevelItemsComponent,
        StateDialogComponent,
        PromptDialogComponent
    ],
    exports: [],
    entryComponents: [
        UserSingleDialogComponent,
        UserMultipleDialogComponent,
        DeptMultipleDialogComponent,
        DeptSingleDialogComponent,
        ConfirmDialogComponent,
        AlertDialogComponent,
        StateDialogComponent,
        PromptDialogComponent
    ],
    providers: [
        DialogsService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DialogsModule {
}
