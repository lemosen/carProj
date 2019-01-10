import {Component, forwardRef, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

export const INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => SingleTagComponent),
    multi: true
};

@Component({
    selector: 'app-single-tag',
    templateUrl: './single-tag.component.html',
    styleUrls: ['./single-tag.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [INPUT_CONTROL_VALUE_ACCESSOR]
})
export class SingleTagComponent implements OnInit, ControlValueAccessor {

    @Input() obj: any;

    @Input() placeholder: '请选择';

    @Input() fieldName: string = 'userName';

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
