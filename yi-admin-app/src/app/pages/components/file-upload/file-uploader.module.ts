import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FileUploadModule} from "ng2-file-upload";
import {SharedModule} from "../../../shared/shared.module";
import {FileUploaderComponent} from "./file-uploader.component";

@NgModule({
    imports: [
        SharedModule,
        FileUploadModule,
    ],
    declarations: [
        FileUploaderComponent
    ],
    exports: [
        FileUploaderComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FileUploaderModule {
}
