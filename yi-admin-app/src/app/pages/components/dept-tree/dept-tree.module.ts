import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {DeptService} from "../../../services/dept.service";
import {DeptTreeComponent} from "./dept-tree.component";

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        DeptTreeComponent
    ],
    exports: [
        DeptTreeComponent
    ],
    providers: [
        DeptService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeptTreeModule {
}
