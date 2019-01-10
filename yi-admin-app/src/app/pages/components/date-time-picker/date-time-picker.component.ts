import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-date-time-picker',
    templateUrl: './date-time-picker.component.html',
    styleUrls: ['./date-time-picker.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class DateTimePickerComponent implements OnInit {
    //Datepicker in a popup
    @Input()
    modelPopup: NgbDateStruct;
    @Output()
    result = new EventEmitter

    @Input()
    initDate = false

    @ViewChild('p')p

    callBack() {
        this.result.emit(this.modelPopup)
    }

    setModelPopup(value: NgbDateStruct) {
        this.modelPopup = value;
    }

    constructor() {
    }

    ngAfterViewInit() {
        if (this.initDate) {
            this.modelPopup = {
                year: new Date().getFullYear(),
                month: new Date().getMonth() + 1,
                day: new Date().getDate() - 3
            }
        }
    }

    ngOnInit() {
    }

}
