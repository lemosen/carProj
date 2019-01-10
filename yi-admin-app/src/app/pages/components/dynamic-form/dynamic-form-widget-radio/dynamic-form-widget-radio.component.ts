import {Component, OnInit} from '@angular/core';
import {DynamicFormWidgetBaseComponent} from "../dynamic-form-widget-base/dynamic-form-widget-base.component";

@Component({
    selector: 'app-dynamic-form-widget-radio',
    templateUrl: './dynamic-form-widget-radio.component.html',
    styleUrls: ['./dynamic-form-widget-radio.component.scss']
})
export class DynamicFormWidgetRadioComponent extends DynamicFormWidgetBaseComponent implements OnInit {

    constructor() {
        super();
    }

    ngOnInit() {
    }

}
