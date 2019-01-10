import {NgModule} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SelectProvinceComponent} from "./select-province.component";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SelectModule} from "../select/select.module";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule,
        SelectModule
    ],
    declarations: [
        SelectProvinceComponent
    ],
    exports: [
        SelectProvinceComponent
    ]
})
export class SelectProvinceModule {
}
