import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {CommentModule} from "../comment/comment.module";
import {AttachmentModule} from "../attachment/attachment.module";
import {ModalSelecetComponent} from "./modal-selecet.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
    imports: [
        SharedModule,
        AttachmentModule,
        CommentModule,
        NgbModule,
    ],
    declarations: [
        ModalSelecetComponent
    ],
    exports: [
        ModalSelecetComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModalSelectModule {
}
