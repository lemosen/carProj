import {Component, OnInit} from '@angular/core';
import {DynamicFormWidgetBaseComponent} from "../dynamic-form-widget-base/dynamic-form-widget-base.component";
import {DialogsService} from '../../dialogs/dialogs.service';

@Component({
    selector: 'app-dynamic-form-widget-choose-tag',
    templateUrl: './dynamic-form-widget-choose-tag.component.html',
    styleUrls: ['./dynamic-form-widget-choose-tag.component.scss']
})
export class DynamicFormWidgetChooseTagComponent extends DynamicFormWidgetBaseComponent implements OnInit {

    constructor(private dialogService: DialogsService) {
        super();
    }

    ngOnInit() {
    }

    dialogChoose(mode, target) {
        if (mode === 'multiple') {
            this.multipleDialogByTarget(target)
        } else {
            this.singleDialogByTarget(target)
        }
    }

    private multipleDialogByTarget(type) {
        switch (type) {
            case 'user':
                this.dialogService.userMultipleChoose(this.control.value).then(result => {
                    if (result) {
                        this.control.setValue(result);
                    }
                });
                break;
            case 'dept':
                this.dialogService.deptMultipleChoose(this.control.value).then(result => {
                    if (result) {
                        this.control.setValue(result);
                    }
                });
                break;
        }
    }

    private singleDialogByTarget(type) {
        switch (type) {
            case 'user':
                this.dialogService.userSingleChoose(this.control.value).then(result => {
                    if (result) {
                        this.control.setValue(result);
                    }
                });
                break;
            case 'dept':
                this.dialogService.deptSingleChoose(this.control.value).then(result => {
                    if (result) {
                        this.control.setValue(result);
                    }
                });
                break;
        }
    }
}
