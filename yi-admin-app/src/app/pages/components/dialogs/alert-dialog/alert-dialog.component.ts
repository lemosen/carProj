import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-alert-dialog',
    templateUrl: './alert-dialog.component.html',
    styleUrls: ['./alert-dialog.component.scss']
})
export class AlertDialogComponent implements OnInit {

    public configData = {
        title: '提示',
        content: '',
        confirmText: '确认'
    };

    constructor(public activeModal: NgbActiveModal) {
    }

    ngOnInit() {
    }

    public onClose() {
        this.activeModal.close();
    }

}
