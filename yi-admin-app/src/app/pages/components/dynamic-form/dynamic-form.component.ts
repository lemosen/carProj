import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {DynamicFormService} from "./dynamic-form.service";
import {ObjectUtils} from "../../../shared/utils/ObjectUtils";

@Component({
    selector: 'app-dynamic-form',
    templateUrl: './dynamic-form.component.html',
    styleUrls: ['./dynamic-form.component.scss'],
    viewProviders: []
})
export class DynamicFormComponent implements OnInit, OnChanges {

    group: FormGroup;

    @Input() options: any [];

    @Output() submitEvent: EventEmitter<any> = new EventEmitter<any>();

    constructor(private dynamicFormService: DynamicFormService) {

    }

    ngOnChanges(value) {
        if (ObjectUtils.isNotEmpty(value.options.currentValue)) {
            this.group = this.dynamicFormService.transform(this.options);
        } else {
            this.group = this.dynamicFormService.transform(this.options);
        }
    }

    ngOnInit() {
    }

    checkDynamicForm() {
        const valid = ObjectUtils.checkValidated(this.group);
        if (valid) {
            this.submitEvent.emit(this.group.value);
            return true;
        }
        return false;
    }

}
