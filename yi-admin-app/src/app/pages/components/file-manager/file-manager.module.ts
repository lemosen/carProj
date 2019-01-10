import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SearchModule} from "../search/search.module";
import {SharedModule} from "../../../shared/shared.module";
import {FileManagerComponent} from "./file-manager.component";
import {FileItemComponent} from "./file-item/file-item.component";
import {AttachmentService} from "../../../services/attachment.service";

@NgModule({
    imports: [
        SharedModule,
        SearchModule,
    ],
    declarations: [
        FileManagerComponent,
        FileItemComponent
    ],
    exports: [
        FileManagerComponent
    ],
    providers: [AttachmentService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FileManagerModule {
}
