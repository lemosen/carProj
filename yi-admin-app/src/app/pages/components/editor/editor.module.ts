import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {EditorComponent} from "./editor.component";
import {CKEditorModule} from "ng2-ckeditor";

@NgModule({
    imports: [
        NgbModule,
        SharedModule,
        CKEditorModule
    ],
    declarations: [
        EditorComponent
    ],
    exports: [
        EditorComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EditorModule {
}
