import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SharedModule} from "../../../shared/shared.module";
import {DynamicFormComponent} from "./dynamic-form.component";
import {DynamicFormWidgetComponent} from './dynamic-form-widget/dynamic-form-widget.component';
import {FormErrorModule} from "../form-error/form-error.module";
import {DynamicFormWidgetBaseComponent} from './dynamic-form-widget-base/dynamic-form-widget-base.component';
import {DynamicFormWidgetTextComponent} from './dynamic-form-widget-text/dynamic-form-widget-text.component';
import {DynamicFormService} from "./dynamic-form.service";
import {DynamicFormWidgetTextAreaComponent} from './dynamic-form-widget-text-area/dynamic-form-widget-text-area.component';
import {TagsModule} from "../tags/tags.module";
import {DialogsModule} from "../dialogs/dialogs.module";
import {DynamicFormWidgetChooseTagComponent} from './dynamic-form-widget-choose-tag/dynamic-form-widget-choose-tag.component';
import {FormErrorComponent} from "../form-error/form-error.component";
import {DynamicFormWidgetSelectComponent} from './dynamic-form-widget-select/dynamic-form-widget-select.component';
import {DynamicFormWidgetRadioComponent} from './dynamic-form-widget-radio/dynamic-form-widget-radio.component';
import {DynamicFormWidgetCardStyleComponent} from './dynamic-form-widget-card-style/dynamic-form-widget-card-style.component';

@NgModule({
    imports: [
        SharedModule,
        FormErrorModule,
        TagsModule,
        DialogsModule,
    ],
    declarations: [
        DynamicFormComponent,
        DynamicFormWidgetComponent,
        DynamicFormWidgetBaseComponent,
        DynamicFormWidgetTextComponent,
        DynamicFormWidgetTextAreaComponent,
        DynamicFormWidgetChooseTagComponent,
        DynamicFormWidgetSelectComponent,
        DynamicFormWidgetRadioComponent,
        DynamicFormWidgetCardStyleComponent,
    ],
    exports: [
        DynamicFormComponent,
        DynamicFormWidgetComponent,
        DynamicFormWidgetBaseComponent,
        DynamicFormWidgetTextComponent,
        DynamicFormWidgetRadioComponent,
        DynamicFormWidgetCardStyleComponent
    ],
    entryComponents: [
        DynamicFormWidgetTextComponent,
        DynamicFormWidgetTextAreaComponent,
        DynamicFormWidgetChooseTagComponent,
        DynamicFormWidgetSelectComponent,
        DynamicFormWidgetRadioComponent,
        DynamicFormWidgetCardStyleComponent,
        FormErrorComponent
    ],
    providers: [DynamicFormService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DynamicFormModule {
}
