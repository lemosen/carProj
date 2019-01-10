import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
    selector: 'app-result-no-level-items',
    templateUrl: './result-no-level-items.component.html',
    styleUrls: ['./result-no-level-items.component.scss']
})
export class ResultNoLevelItemsComponent implements OnInit {

    @Input() items: Array<any>;

    @Input() fieldName: string = 'userName';

    @Output() onChooseResult: EventEmitter<any> = new EventEmitter();

    constructor() {
    }

    ngOnInit() {
    }

    getText(obj: any) {
        return obj[this.fieldName];
    }

    chooseItem(item) {
        this.onChooseResult.emit(item);
    }
}
