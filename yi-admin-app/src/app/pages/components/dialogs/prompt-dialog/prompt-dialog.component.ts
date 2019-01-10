import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-prompt-dialog',
    templateUrl: './prompt-dialog.component.html',
    styleUrls: ['./prompt-dialog.component.scss']
})
export class PromptDialogComponent implements OnInit {

    public configData = {
        title: '提示',
        placeholder: '',
        text: '',
        confirmText: '确认',
        cancelText: '取消'
    };

    promptText: string = "";

    constructor(public bsModalRef: NgbModalRef) {
    }

    ngOnInit() {
    }

    getTextValue(event) {
        console.log(event.target.value);
        this.promptText = event.target.value;
    }

    public onClose() {
        this.bsModalRef.close();
    }

    public onAccept() {
        this.bsModalRef.close(this.promptText);
    }

}
