import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';

@Component({
    selector: 'app-toggle-show',
    templateUrl: './toggle-show.component.html',
    styleUrls: ['./toggle-show.component.scss']
})
export class ToggleShowComponent implements OnInit, OnChanges {

    @Input() title: string;
    @Input() showDetail: boolean = false;

    @Output() toggled: EventEmitter<boolean> = new EventEmitter();

    // @ContentChild(TemplateRef) template: TemplateRef<any>;

    showDetailLabel: string = '展开';

    constructor() {
    }

    ngOnChanges(value) {
        if (value.showDetail !== undefined && value.showDetail.currentValue !== undefined) {
            this.showDetailLabel = this.showDetail ? '收拢' : '展开';
        }
    }

    ngOnInit() {
    }

    showOrHiddenDetail() {
        this.showDetail = !this.showDetail;
        this.showDetailLabel = this.showDetail ? '收拢' : '展开';

        this.toggled.emit(this.showDetail);
    }
}
