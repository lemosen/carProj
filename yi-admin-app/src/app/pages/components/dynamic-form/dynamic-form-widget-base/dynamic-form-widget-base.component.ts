import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'app-dynamic-form-widget-base',
    template: ''
})
export class DynamicFormWidgetBaseComponent implements OnInit {

    @Input() control;

    @Input() options;

    constructor() {
    }

    ngOnInit() {
    }

    ngOnChanges(value) {
    }


}
