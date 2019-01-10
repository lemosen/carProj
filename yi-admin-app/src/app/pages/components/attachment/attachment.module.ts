import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {FileUploadModule} from "ng2-file-upload";
import {AttachmentViewComponent} from './attachment-view/attachment-view.component';
import {AttachmentUploadComponent} from './attachment-upload/attachment-upload.component';
import {AttachmentItemComponent} from './attachment-item/attachment-item.component';
import {TipsModule} from "../tips/tips.module";


@NgModule({
    imports: [
        SharedModule,
        FileUploadModule,
        TipsModule
    ],
    declarations: [
        AttachmentViewComponent,
        AttachmentUploadComponent,
        AttachmentItemComponent
    ],
    exports: [
        AttachmentViewComponent,
        AttachmentUploadComponent,
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AttachmentModule {
}
