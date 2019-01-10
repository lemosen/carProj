import {Component, Input, OnInit} from '@angular/core';
import {PageQuery} from "../../models/page-query.model";
import {AttachmentService} from "../../../services/attachment.service";
import {ObjectUtils} from "../../../shared/utils/ObjectUtils";
import {AttachmentVo} from "../../models/domain/vo/attachment-vo.model";
import {IMultiSelectTexts} from 'angular-2-dropdown-multiselect';

@Component({
    selector: 'app-file-manager',
    templateUrl: './file-manager.component.html',
    styleUrls: ['./file-manager.component.scss']
})
export class FileManagerComponent implements OnInit {

    @Input() showRecordNum: boolean = false;

    @Input() objectPath: string = '';

    fileTypeSelectTexts: IMultiSelectTexts = {
        defaultTitle: "全部"
    };

    fileTypeDropdownList = [
        {name: "文档文件", id: "DOCUMENT"},
        {name: "压缩文件", id: "COMPRESSION"},
        {name: "图片", id: "PICTURES"},
        {name: "视频", id: "VIDEOS"},
        {name: "其他", id: "OTHERS"},
    ];

    fileTypeSelectedItems = [];

    fileExtSelectTexts: IMultiSelectTexts = {
        defaultTitle: "全部"
    };

    fileExtDropdownList = [
        {name: "TXT", id: "TXT"},
        {name: "JPG", id: "JPG"},
        {name: "JPEG", id: "JPEG"},
        {name: "PNG", id: "PNG"},
    ];

    fileExtSelectedItems = [];

    searchText: string;

    pageQuery: PageQuery = new PageQuery;

    attachments: Array<AttachmentVo>;

    constructor(private attachmentService: AttachmentService) {
    }

    ngOnChanges(value) {
        if (value.objectPath.currentValue !== undefined) {
            this.getDatas();
        }
    }

    ngOnInit() {
        this.attachmentService.onRefreshFileManager.subscribe(result => {
            if (result) {
                this.getDatas();
            }
        })
    }

    searchData(event) {
        this.searchText = event;
        this.getDatas();
    }

    fileTypeChanged(newStates) {
        this.fileTypeSelectedItems = newStates;
        this.getDatas();
    }

    fileExtChanged(newStates) {
        this.fileExtSelectedItems = newStates;
        this.getDatas();
    }

    pageChange(event) {
        this.pageQuery.page = event.page;
        this.getDatas();
    }

    getDatas() {
        this.pageQuery.clearParamsRequests();
        if (ObjectUtils.isNotEmpty(this.objectPath)) {
            this.pageQuery.pushParamsRequests('objectPath', this.objectPath + "%");
        }
        if (ObjectUtils.isNotEmpty(this.fileTypeSelectedItems)) {
            this.pageQuery.pushParamsRequests('fileTypes', this.fileTypeSelectedItems);
        }
        if (ObjectUtils.isNotEmpty(this.fileExtSelectedItems)) {
            this.pageQuery.pushParamsRequests('fileExts', this.fileExtSelectedItems);
        }
        if (this.searchText) {
            this.pageQuery.pushParamsRequests('fileName', this.searchText);
        }
        this.attachmentService.query(this.pageQuery).subscribe(response => {
            this.attachments = response.content;
            this.pageQuery.covertResponses(response);
        });
    }
}
