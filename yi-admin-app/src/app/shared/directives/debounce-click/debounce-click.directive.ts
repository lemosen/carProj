import {Directive, EventEmitter, HostListener, Input, Output} from '@angular/core';
import {Subject} from "rxjs/Subject";
import {Subscription} from "rxjs/Subscription";

@Directive({
    selector: '[appDebounceClick]'
})
export class DebounceClickDirective {
    @Input() debounceTime = 500;
    @Output() debounceClick = new EventEmitter();

    private clicks = new Subject<any>();
    private subscription: Subscription;

    constructor() {
    }

    ngOnInit() {
        this.subscription = this.clicks
            .debounceTime(this.debounceTime)
            .subscribe(e => this.debounceClick.emit(e));
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    @HostListener('click', ['$event'])
    clickEvent(event: MouseEvent) {
        event.preventDefault();
        event.stopPropagation();
        this.clicks.next(event);
    }

}
