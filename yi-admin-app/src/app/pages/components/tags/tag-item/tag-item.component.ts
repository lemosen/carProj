import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
    selector: 'app-tag-item',
    templateUrl: './tag-item.component.html',
    styleUrls: ['./tag-item.component.scss']
})
export class TagItemComponent implements OnInit {

    @Input() obj: any;

    @Input() text: string;

    @Input() showRemove: boolean = true;

    @Output() tagRemoved: EventEmitter<number> = new EventEmitter();

    constructor() {
    }

    ngOnInit() {
    }

    removeTag() {
        this.tagRemoved.emit(this.obj);
    }
}
