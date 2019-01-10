import {Component, Input, OnInit} from '@angular/core';
import {AttachmentVo} from "../../../models/domain/vo/attachment-vo.model";
import {AttachmentService} from "../../../../services/attachment.service";

@Component({
    selector: 'app-file-item',
    templateUrl: './file-item.component.html',
    styleUrls: ['./file-item.component.scss']
})
export class FileItemComponent implements OnInit {

    @Input() attachment: AttachmentVo;

    constructor(private attachmentService: AttachmentService) {
    }

    ngOnInit() {
    }

    objectNames(attach: AttachmentVo) {
        // let objectInfos = attach.objectInfos;
        // if (objectInfos == null) return "";
        // if (objectInfos.length == 1) return objectInfos[0].objectName;
        //
        // let names: Array<string> = [];
        // for (let index = 0; index < objectInfos.length; ++index) {
        //   names.push(objectInfos[index].objectName);
        // }
        // return names.join(" / ");
    }

    downloadFile(attach) {
        // this.attachmentService.download("GET", attach.fileName, attach.url, null);
    }

}
