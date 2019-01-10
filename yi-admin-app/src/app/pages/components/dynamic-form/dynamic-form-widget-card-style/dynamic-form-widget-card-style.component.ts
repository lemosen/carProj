import {Component, OnInit} from '@angular/core';
import {DynamicFormWidgetBaseComponent} from "../dynamic-form-widget-base/dynamic-form-widget-base.component";
import {FormBuilder} from "@angular/forms";

@Component({
    selector: 'app-dynamic-form-widget-card-style',
    templateUrl: './dynamic-form-widget-card-style.component.html',
    styleUrls: ['./dynamic-form-widget-card-style.component.scss']
})
export class DynamicFormWidgetCardStyleComponent extends DynamicFormWidgetBaseComponent implements OnInit {

    formErrors = {
        styleFieldTitle: [{
            name: 'required',
            msg: '不可为空',
        }],
        width: [{
            name: 'required',
            msg: '不可为空',
        }],
        showOrder: [{
            name: 'required',
            msg: '不可为空',
        }],
    };

    constructor(private fb: FormBuilder) {
        super();
    }

    ngOnChanges(value) {
    }

    ngOnInit() {
    }

}
