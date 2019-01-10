import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SharedModule} from "../../shared/shared.module";
import {DialogsModule} from "./dialogs/dialogs.module";
import {CommentModule} from "./comment/comment.module";
import {AttachmentModule} from "./attachment/attachment.module";
import {ObjectLogModule} from "./object-log/object-log.module";
import {TabModule} from "./tab/tab.module";
import {TagsModule} from "./tags/tags.module";
import {SearchModule} from "./search/search.module";
import {DeptBreadcrumbModule} from "./dialogs/breadcrumb/dept-breadcrumb/dept-breadcrumb.module";

import {FormErrorModule} from "./form-error/form-error.module";
import {DynamicFormModule} from "./dynamic-form/dynamic-form.module";
import {FileManagerModule} from "./file-manager/file-manager.module";

import {ToggleShowModule} from "./toggle-show/toggle-show.module";
import {FileUploaderModule} from "./file-upload/file-uploader.module";
import {TipsModule} from "./tips/tips.module";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ModalSelectModule} from "./modal-selecet/modal-select.module";
import {DateTimePickerModule} from "./date-time-picker/date-time-picker.module";
import {EditorModule} from "./editor/editor.module";
import { TreeComponent } from './tree/tree.component';
import {TreeModule} from "./tree/tree.module";
import { ProvinceComponent } from './province/province.component';
import {ProvinceModule} from "./province/province.module";
import {JurisdictionModule} from "./jurisdiction/jurisdiction.module";

@NgModule({
    imports: [
        SharedModule,
        NgbModule,
        DialogsModule
    ],
    declarations: [],
    exports: [
        NgbModule,
        AttachmentModule,
        CommentModule,
        FileManagerModule,
        ObjectLogModule,
        FileUploaderModule,
        SearchModule,
        ModalSelectModule,
        TabModule,
        TagsModule,
        DeptBreadcrumbModule,
        FormErrorModule,
        DynamicFormModule,
        ToggleShowModule,
        TipsModule,
        DateTimePickerModule,
        EditorModule,
        TreeModule,
        ProvinceModule,
        JurisdictionModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ComponentsModule {
}
