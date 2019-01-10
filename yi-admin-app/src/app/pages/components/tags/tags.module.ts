import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {TagItemComponent} from './tag-item/tag-item.component';
import {DeptMultipleTagComponent} from './multiple-tag/dept-multiple-tag/dept-multiple-tag.component';
import {UserMultipleTagComponent} from './multiple-tag/user-multiple-tag/user-multiple-tag.component';
import {SingleTagComponent} from './single-tag/single-tag.component';
import {MultipleTagComponent} from './multiple-tag/multiple-tag.component';
import {SharedModule} from '../../../shared/shared.module';
import {UserSingleTagComponent} from './single-tag/user-single-tag/user-single-tag.component';
import {DeptSingleTagComponent} from './single-tag/dept-single-tag/dept-single-tag.component';

@NgModule({
    imports: [
        SharedModule
    ],
    declarations: [
        TagItemComponent,
        MultipleTagComponent,
        SingleTagComponent,
        DeptMultipleTagComponent,
        UserMultipleTagComponent,
        UserSingleTagComponent,
        DeptSingleTagComponent
    ],
    exports: [
        TagItemComponent,
        MultipleTagComponent,
        SingleTagComponent,
        DeptMultipleTagComponent,
        UserMultipleTagComponent,
        UserSingleTagComponent,
        DeptSingleTagComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TagsModule {
}
