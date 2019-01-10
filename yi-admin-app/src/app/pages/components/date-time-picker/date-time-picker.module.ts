import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {DateTimePickerComponent} from "./date-time-picker.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
    imports: [
        NgbModule,
        SharedModule
    ],
    declarations: [
        DateTimePickerComponent
    ],
    exports: [
        DateTimePickerComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DateTimePickerModule {
}
