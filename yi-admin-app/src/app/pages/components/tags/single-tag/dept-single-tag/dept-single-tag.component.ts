import {Component, forwardRef, Input, OnInit} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

export const INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DeptSingleTagComponent),
    multi: true
};

@Component({
    selector: 'app-dept-single-tag',
    templateUrl: './dept-single-tag.component.html',
    styleUrls: ['./dept-single-tag.component.scss'],
    providers: [INPUT_CONTROL_VALUE_ACCESSOR]
})
export class DeptSingleTagComponent implements OnInit, ControlValueAccessor {

    @Input() obj: any;

    @Input() placeholder: '请选择';

    @Input() fieldName: string = 'deptName';

    @Input() showRemove: boolean = true;
    /** Implemented as part of ControlValueAccessor. */
    onChange: (value) => any = () => {
    };
    onTouched: () => any = () => {
    };

    constructor() {
    }

    ngOnInit() {

    }

    getText(obj: any) {
        return obj[this.fieldName];
    }

    removeTag(event): void {
        this.obj = null;
        this.onChange(this.obj);
    }

    writeValue(value: any) {
        if (value) {
            this.obj = value;
        }
    }

    registerOnChange(fn: any) {
        this.onChange = fn;
    }

    registerOnTouched(fn: any) {
        this.onTouched = fn;
    }
}
