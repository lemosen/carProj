import {NgModule} from '@angular/core';
import {SharedModule} from "../../../../../shared/shared.module";
import {DeptBreadcrumbShowChildComponent} from './dept-breadcrumb-show-child/dept-breadcrumb-show-child.component';
import {DeptBreadcrumbNoChildComponent} from './dept-breadcrumb-no-child/dept-breadcrumb-no-child.component';

@NgModule({
    imports: [
        SharedModule,
    ],
    declarations: [
        DeptBreadcrumbShowChildComponent,
        DeptBreadcrumbNoChildComponent
    ],
    exports: [
        DeptBreadcrumbShowChildComponent,
        DeptBreadcrumbNoChildComponent
    ],
    providers: []
})
export class DeptBreadcrumbModule {
}
