import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {TreeComponent} from "./tree.component";

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        TreeComponent
    ],
    exports: [
        TreeComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TreeModule {
}
