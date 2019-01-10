import {Component, OnInit} from '@angular/core';
import {DynamicFormWidgetBaseComponent} from "../dynamic-form-widget-base/dynamic-form-widget-base.component";

@Component({
    selector: 'app-dynamic-form-widget-select',
    templateUrl: './dynamic-form-widget-select.component.html',
    styleUrls: ['./dynamic-form-widget-select.component.scss']
})
export class DynamicFormWidgetSelectComponent extends DynamicFormWidgetBaseComponent implements OnInit {

    constructor() {
        super();
    }

    ngOnInit() {
    }

}
