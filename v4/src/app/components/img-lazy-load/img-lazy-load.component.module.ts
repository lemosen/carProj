import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ImgLazyLoadComponent} from "./img-lazy-load.component";
import {PipesModule} from "../../../pipes/pipes.module";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
    ],
    declarations: [
        ImgLazyLoadComponent
    ],
    exports: [
        ImgLazyLoadComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ImgLazyLoadModule {
}
