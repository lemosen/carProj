import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {NoDataTipComponent} from './no-data-tip/no-data-tip.component';
import {SharedModule} from "../../../shared/shared.module";

@NgModule({
    imports: [
        SharedModule,
    ],
    declarations: [
        NoDataTipComponent
    ],
    exports: [
        NoDataTipComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TipsModule {
}
