import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {CheckallDirective} from './checkall/checkall.directive';
import {ClickStopDirective} from './click-stop/click-stop.directive';
import {SparklineDirective} from './sparkline/sparkline.directive';
import {DebounceClickDirective} from './debounce-click/debounce-click.directive';
import {WidgetDirective} from './widget/widget.directive';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
    ],
    declarations: [
        SparklineDirective,
        CheckallDirective,
        DebounceClickDirective,
        ClickStopDirective,
        WidgetDirective,
    ],
    exports: [
        SparklineDirective,
        CheckallDirective,
        DebounceClickDirective,
        ClickStopDirective,
        WidgetDirective,
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedDirectivesModule {
}
