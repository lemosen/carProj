import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {AttachmentVo} from "../../../models/domain/vo/attachment-vo.model";
import {DialogsService} from "../../dialogs/dialogs.service";
import {AttachmentService} from "../../../../services/attachment.service";

@Component({
    selector: 'app-attachment-view',
    templateUrl: './attachment-view.component.html',
    styleUrls: ['./attachment-view.component.scss']
})
export class AttachmentViewComponent implements OnInit, OnChanges {

    @Input() noBorder: boolean = false;

    @Input() objectPath: string;

    @Input() attachments: Array<AttachmentVo> = new Array();

    allowRemove: boolean = false;

    constructor(private attachmentService: AttachmentService, private dialogService: DialogsService) {
    }

    ngOnChanges(value) {
        // if (value.objectPath !== undefined) {
        //   this.attachmentService.findByPath({objectPath: this.objectPath}).subscribe(response => {
        //     if (response.result == SUCCESS) {
        //       this.attachments = response.data;
        //     } else {
        //       this.dialogService.alert('请求失败', response.message);
        //     }
        //   }, error => {
        //     this.dialogService.alert('请求错误', error.message);
        //   });
        // }
    }

    ngOnInit() {
    }

}
