import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AttachmentVo} from "../../../models/domain/vo/attachment-vo.model";
import {AttachmentService} from "../../../../services/attachment.service";

@Component({
    selector: 'app-attachment-item',
    templateUrl: './attachment-item.component.html',
    styleUrls: ['./attachment-item.component.scss']
})
export class AttachmentItemComponent implements OnInit {

    @Input() allowRemove: boolean = true;

    @Input() attachments: Array<AttachmentVo> = new Array();

    @Output() onRemoveAttachment: EventEmitter<AttachmentVo> = new EventEmitter();

    constructor(private attachmentService: AttachmentService) {
    }

    ngOnInit() {
    }

    removeAttach(attach) {
        this.onRemoveAttachment.emit(attach);
    }

    downloadFile(attach) {
        console.log("downloadFile attach=" + JSON.stringify(attach));
        this.attachmentService.download("GET", attach.fileName, attach.url, null);
    }

    viewFile(attach) {
        console.log("viewFile attach=" + JSON.stringify(attach));
    }
}
