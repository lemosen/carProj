import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {ToggleShowComponent} from './toggle-show.component';
import {SharedModule} from "../../../shared/shared.module";

@NgModule({
    imports: [
        SharedModule,
    ],
    declarations: [
        ToggleShowComponent,
    ],
    exports: [
        ToggleShowComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ToggleShowModule {
}
