import {NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {ProvinceComponent} from "./province.component";
import {SelectModule} from "../../../shared/components/select/select.module";

@NgModule({
    imports: [
        SharedModule,SelectModule
    ],
    declarations: [
        ProvinceComponent
    ],
    exports: [
        ProvinceComponent
    ],
    providers: []
})
export class ProvinceModule {
}
