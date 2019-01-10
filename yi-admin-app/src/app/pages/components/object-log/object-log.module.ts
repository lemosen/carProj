import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {ObjectLogComponent} from "./object-log.component";
import {ObjectLogService} from "./object-log.service";
import {ObjectLogItemComponent} from './object-log-item/object-log-item.component';
import {TipsModule} from "../tips/tips.module";
import {SharedPipesModule} from '../../../shared/pipes/shared-pipes.module';

@NgModule({
    imports: [
        SharedModule,
        SharedPipesModule,
        TipsModule
    ],
    declarations: [
        ObjectLogComponent,
        ObjectLogItemComponent
    ],
    exports: [
        ObjectLogComponent
    ],
    providers: [
        ObjectLogService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObjectLogModule {
}
