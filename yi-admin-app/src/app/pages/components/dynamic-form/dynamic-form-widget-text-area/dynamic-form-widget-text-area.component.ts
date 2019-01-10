import {Component, OnInit} from '@angular/core';
import {DynamicFormWidgetBaseComponent} from "../dynamic-form-widget-base/dynamic-form-widget-base.component";

@Component({
    selector: 'app-dynamic-form-widget-text-area',
    templateUrl: './dynamic-form-widget-text-area.component.html',
    styleUrls: ['./dynamic-form-widget-text-area.component.scss']
})
export class DynamicFormWidgetTextAreaComponent extends DynamicFormWidgetBaseComponent implements OnInit {

    constructor() {
        super();
    }

    ngOnInit() {
    }

}
