import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'app-no-data-tip',
    templateUrl: './no-data-tip.component.html',
    styleUrls: ['./no-data-tip.component.scss']
})
export class NoDataTipComponent implements OnInit {

    @Input() isShow = true;

    @Input() text = '暂无数据';

    constructor() {
    }

    ngOnInit() {
    }

}
