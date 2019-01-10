import {Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

const VALUE_ACCESSOR = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => SelectComponent),
    multi: true
};

/**
 * 基于静态字典数据的单选框, 取代原生的select
 */
@Component({
    selector: 'app-select',
    templateUrl: './select.component.html',
    styleUrls: ['./select.component.scss'],
    providers: [VALUE_ACCESSOR]
})
export class SelectComponent implements OnInit, OnChanges, ControlValueAccessor {
    /**
     * 回调赋值字段
     * @type {string}
     */
    @Input() idField: string = "code";
    /**
     * 显示字段名
     * @type {string}
     */
    @Input() titleField: string = "title";
    @Input() items: Array<any> = [];

    @Input() placeholder: string = "选择";

    @Input() width: string;   // button 的宽度
    @Input() classNames: string = "";  // button 的自定义类名称


    @Input() value: any;
    /**
     * 回调获取返回值方法 tip：(valueChange)="youFunction($event)"
     * @type {EventEmitter<any>}
     */
    @Output() valueChange = new EventEmitter<any>();

    title: string;
    onModelChange: Function = (_: any) => {
    };
    onModelTouched: Function = () => {
    };

    constructor() {
    }

    ngOnInit() {
    }


    ngOnChanges(changes: SimpleChanges): void {
        if ((changes.value && changes.value.currentValue !== undefined)
            || (changes.items && changes.items.currentValue !== undefined)
            || (changes.placeholder && changes.placeholder.currentValue !== undefined)
            || (changes.idField && changes.idField.currentValue !== undefined)
            || (changes.titleField && changes.titleField.currentValue !== undefined)) {
            this.configTitle();
        }


    }

    getButtonStyle() {
        let width = this.width;
        if (width && width.trim().length > 0) {
            return {
                width: width
            };
        }
    }

    itemClick(item, i) {
        this.value = item ? item[this.idField] : null;

        this.title = item ? item[this.titleField] : this.placeholder;
        this.fireModelChange();
    }

    writeValue(value: any): void {
        this.value = value;

        this.configTitle();
    }

    registerOnChange(fn: Function): void {
        this.onModelChange = fn;
    }

    registerOnTouched(fn: Function): void {
        this.onModelTouched = fn;
    }

    private configTitle() {
        this.title = this.placeholder;

        let items = this.items;
        if (this.value == null || this.idField == null || this.titleField == null || items == null || items.length === 0) {
            return;
        }

        for (let index = 0; index < items.length; ++index) {
            if (items[index][this.idField] == this.value) {
                this.title = items[index][this.titleField];
                break;
            }
        }
    }

    private fireModelChange() {
        this.valueChange.emit(this.value);

        this.onModelChange(this.value);
        this.onModelTouched();
    }

}
