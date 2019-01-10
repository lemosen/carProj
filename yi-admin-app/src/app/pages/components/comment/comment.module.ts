import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {CommentComponent} from "./comment.component";
import {AttachmentModule} from "../attachment/attachment.module";
import {CommentItemComponent} from './comment-item/comment-item.component';
import {TagsModule} from "../tags/tags.module";
import {SharedPipesModule} from '../../../shared/pipes/shared-pipes.module';

@NgModule({
    imports: [
        SharedModule,
        AttachmentModule,
        SharedPipesModule,
        TagsModule
    ],
    declarations: [
        CommentComponent,
        CommentItemComponent
    ],
    exports: [
        CommentComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommentModule {
}
