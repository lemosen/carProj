import {Directive, EventEmitter, HostListener, OnDestroy, OnInit, Output} from '@angular/core';

@Directive({
    selector: '[click.stop]'
})
export class ClickStopDirective implements OnInit, OnDestroy {
    @Output("click.stop") stopPropEvent = new EventEmitter();

    constructor() {
    }

    ngOnInit() {
    }

    ngOnDestroy() {
    }

    @HostListener('click', ['$event'])
    clickEvent(event: MouseEvent) {
        event.preventDefault();
        event.stopPropagation();
        this.stopPropEvent.emit(event);
    }

}
