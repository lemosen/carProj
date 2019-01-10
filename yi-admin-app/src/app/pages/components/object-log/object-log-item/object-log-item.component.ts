import {Component, Input, OnInit} from '@angular/core';
import {BusinessLogVo} from "../../../models/domain/vo/business-log-vo.model";

@Component({
    selector: 'app-object-log-item',
    templateUrl: './object-log-item.component.html',
    styleUrls: ['./object-log-item.component.scss']
})
export class ObjectLogItemComponent implements OnInit {

    @Input() record: BusinessLogVo;

    constructor() {
    }

    ngOnInit() {
    }

}
