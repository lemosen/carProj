import {Component, ElementRef, EventEmitter, forwardRef, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';

import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {AppStorage} from '../../../pages/configs/app-storage';

declare var $: any;

export const RICH_EDITOR_VALUE_ACCESSOR = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => RichEditorComponent),
    multi: true
};

@Component({
    selector: 'rich-editor',
    templateUrl: './rich-editor.component.html',
    styleUrls: ['./rich-editor.component.scss'],
    providers: [RICH_EDITOR_VALUE_ACCESSOR]
})
export class RichEditorComponent implements OnInit, OnDestroy, ControlValueAccessor {

    @Input() height: number;
    @Input() minHeight: number;
    @Input() maxHeight: number;
    @Input() placeholder: string;
    @Input() focus: boolean;
    @Input() airMode: boolean;
    @Input() lang: string;

    @Output() valueChange = new EventEmitter<string>();
    @ViewChild('container') container: ElementRef;
    private onTouched = () => {
    };
    private onChange: (value: string) => void = () => {
    };

    constructor(private element: ElementRef, private appStorage: AppStorage) {

    }

    private _value: string;

    get value(): string {
        return this._value;
    }

    set value(value: string) {
        this._value = value;
    }

    private _disabled: boolean = false;

    get disabled(): boolean {
        return this._disabled;
    }

    @Input()
    set disabled(disabled: boolean) {
        this._disabled = disabled;
        $(this.container.nativeElement).summernote(disabled ? 'disable' : 'enable');
    }

    private _options: any;

    get options(): any {
        return this._options || {};
    }

    @Input()
    set options(options: any) {
        this._options = options;
        this.refreshOptions();
    }

    getCode(): string {
        return $(this.container.nativeElement).summernote('code');
    }

    writeValue(code: string) {
        this.value = code;

        if (code == null || code.trim().length === 0) {
            $(this.container.nativeElement).summernote('empty');

        } else {
            $(this.container.nativeElement).summernote('code', code);
        }
    }

    setDisabledState(isDisabled: boolean) {
        this.disabled = isDisabled;
    }

    registerOnChange(fn: any) {
        this.onChange = fn;
    }

    registerOnTouched(fn: any) {
        this.onTouched = fn;
    }

    ngOnInit() {
        this.refreshOptions();
    }

    ngOnDestroy() {
        $(this.container.nativeElement).summernote('destroy');
    }

    private isEmpty() {
        let empty = <boolean>(<any>$(this.container.nativeElement).summernote('isEmpty'));
        return empty;
    }

    private refreshOptions() {
        let options = Object.assign({}, this.appStorage.richEditorOptions, this.options);
        if (this.height > 0) {
            options.height = this.height;
        }
        if (this.minHeight > 0) {
            options.minHeight = this.minHeight;
        }
        if (this.maxHeight > 0) {
            options.maxHeight = this.maxHeight;
        }

        if (this.placeholder != null) {
            options.placeholder = this.placeholder;
        }
        if (this.focus != null) {
            options.focus = this.focus;
        }
        if (this.airMode != null) {
            options.airMode = this.airMode;
        }
        if (this.lang != null) {
            options.lang = this.lang;
        }

        this.addCallbacks(options);

        $(this.container.nativeElement).summernote(options);
        // $(this.element.nativeElement).find(".note-editor").removeClass("panel").removeClass("panel-default").addClass("mt-sub-1");

        // if (options.tooltip != undefined && !options.tooltip)
        //   (<any>$(this.element.nativeElement).find('.note-editor button.note-btn')).tooltip('destroy');
    }

    private addCallbacks(options: any) {
        options.callbacks = {
            onChange: (contents, $editable) => {
                if (this.isEmpty()) {
                    this.onChange(null);
                    this.valueChange.emit(null);

                } else {
                    this.onChange(contents);
                    this.valueChange.emit(contents);
                }
            },

            onBlur: () => {
                this.onTouched();
            }
        };
    }
}
