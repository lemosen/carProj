import {Component, EventEmitter, forwardRef, Input, OnInit, Output} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

export const INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => UserMultipleTagComponent),
    multi: true
};

@Component({
    selector: 'app-user-multiple-tag',
    templateUrl: './user-multiple-tag.component.html',
    styleUrls: ['./user-multiple-tag.component.scss'],
    providers: [INPUT_CONTROL_VALUE_ACCESSOR]
})
export class UserMultipleTagComponent implements OnInit, ControlValueAccessor {

    @Input() objs: Array<any>;

    @Input() placeholder: '请选择';

    @Input() fieldName: string = 'userName';

    @Input() showRemove: boolean = true;

    @Output() onRemoveItem: EventEmitter<number> = new EventEmitter<number>();
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
            this.onRemoveItem.next(fruit);
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
