import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-confirm-dialog',
    templateUrl: './confirm-dialog.component.html',
    styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent implements OnInit {

    public configData = {
        title: '提示',
        content: '',
        confirmText: '确认',
        cancelText: '取消'
    };

    constructor(public activeModal: NgbActiveModal) {
    }

    ngOnInit() {
    }

    public onClose() {
        this.activeModal.close(false);
    }

    public onAccept() {
        this.activeModal.close(true);
    }

}
