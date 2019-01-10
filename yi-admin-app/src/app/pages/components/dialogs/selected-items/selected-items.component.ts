import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
    selector: 'app-selected-items',
    templateUrl: './selected-items.component.html',
    styleUrls: ['./selected-items.component.scss']
})
export class SelectedItemsComponent implements OnInit {

    @Input() items: Array<any>;

    @Input() fieldName: string = 'userName';

    @Input() showRemove: boolean = true;

    @Output() onRemoveItemResult: EventEmitter<any> = new EventEmitter();

    constructor() {
    }

    ngOnInit() {
    }

    getText(obj: any) {
        return obj[this.fieldName];
    }

    removeItem(item) {
        this.onRemoveItemResult.emit(item);
    }
}
