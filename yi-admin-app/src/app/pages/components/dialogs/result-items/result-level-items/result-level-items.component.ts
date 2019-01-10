import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
    selector: 'app-result-level-items',
    templateUrl: './result-level-items.component.html',
    styleUrls: ['./result-level-items.component.scss']
})
export class ResultLevelItemsComponent implements OnInit {

    @Input() items: Array<any>;

    @Input() fieldName: string = 'deptName';

    @Output() onChooseResult: EventEmitter<any> = new EventEmitter();

    @Output() onChooseChildLevel: EventEmitter<any> = new EventEmitter();

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

    chooseChildLevel(item) {
        this.onChooseChildLevel.emit(item);
    }

}
