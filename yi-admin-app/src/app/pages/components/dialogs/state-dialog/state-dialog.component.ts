import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-state-dialog',
    templateUrl: './state-dialog.component.html',
    styleUrls: ['./state-dialog.component.scss']
})
export class StateDialogComponent implements OnInit {

    public configData = {
        title: '选择',
        confirmText: '确认',
        cancelText: '取消'
    };

    states: Array<any>;

    chooseState: any;

    constructor(public bsModalRef: NgbModalRef) {
    }

    ngOnInit() {
    }

    public onClose() {
        this.bsModalRef.close();
    }

    chooseItem(item) {
        this.chooseState = item;
    }

    public onAccept() {
        this.bsModalRef.close(this.chooseState);
    }

}
