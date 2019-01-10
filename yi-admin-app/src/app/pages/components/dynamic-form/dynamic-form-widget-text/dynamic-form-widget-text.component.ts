import {Component, OnInit} from '@angular/core';
import {DynamicFormWidgetBaseComponent} from "../dynamic-form-widget-base/dynamic-form-widget-base.component";

@Component({
    selector: 'app-dynamic-form-widget-text',
    templateUrl: './dynamic-form-widget-text.component.html',
    styleUrls: ['./dynamic-form-widget-text.component.scss']
})
export class DynamicFormWidgetTextComponent extends DynamicFormWidgetBaseComponent implements OnInit {

    constructor() {
        super();
    }

    ngOnInit() {
    }

}
