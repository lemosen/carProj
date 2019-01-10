import {Component, forwardRef, Input, OnInit} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

export const INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DeptMultipleTagComponent),
    multi: true
};

@Component({
    selector: 'app-dept-multiple-tag',
    templateUrl: './dept-multiple-tag.component.html',
    styleUrls: ['./dept-multiple-tag.component.scss'],
    providers: [INPUT_CONTROL_VALUE_ACCESSOR]
})
export class DeptMultipleTagComponent implements OnInit, ControlValueAccessor {

    @Input() objs: Array<any>;

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

    removeTag(fruit: any): void {
        let index = this.objs.indexOf(fruit);
        if (index >= 0) {
            this.objs.splice(index, 1);
        }
        this.onChange(this.objs);
    }

    writeValue(value: any) {
        if (value) {
            this.objs = value;
        }
    }

    registerOnChange(fn: any) {
        this.onChange = fn;
    }

    registerOnTouched(fn: any) {
        this.onTouched = fn;
    }
}
