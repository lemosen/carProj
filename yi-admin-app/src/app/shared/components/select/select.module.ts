import {NgModule} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SelectComponent} from "./select.component";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule
    ],
    declarations: [
        SelectComponent
    ],
    exports: [
        SelectComponent
    ]
})
export class SelectModule {
}
