import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FormErrorComponent} from "./form-error.component";
import {SharedModule} from "../../../shared/shared.module";

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        FormErrorComponent
    ],
    exports: [
        FormErrorComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FormErrorModule {
}
