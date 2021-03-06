import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {IndexListComponent} from "./index-list.component";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule
    ],
    declarations: [
        IndexListComponent
    ],
    exports: [
        IndexListComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IndexListModule {
}
