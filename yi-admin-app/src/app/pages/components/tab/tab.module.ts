import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {CommentModule} from "../comment/comment.module";
import {AttachmentModule} from "../attachment/attachment.module";
import {TabService} from "./tab.service";
import {TabComponent} from "./tab.component";

@NgModule({
    imports: [
        SharedModule,
        AttachmentModule,
        CommentModule
    ],
    declarations: [
        TabComponent
    ],
    exports: [
        TabComponent
    ],
    providers: [TabService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TabModule {
}
