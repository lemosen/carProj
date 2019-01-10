import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {AppStorage} from "../../../configs/app-storage";
import {AttachmentVo} from "../../../models/domain/vo/attachment-vo.model";
import {FileItem, FileLikeObject, FileUploader, FileUploaderOptions, ParsedResponseHeaders} from "ng2-file-upload";
import {Utils} from "../../../../shared/utils/Utils";
import {DialogsService} from "../../dialogs/dialogs.service";
import {SUCCESS} from "../../../models/constants.model";
import {AppConfig} from "../../../configs/app-config";
import {ObjectUtils} from "../../../../shared/utils/ObjectUtils";

@Component({
    selector: 'app-attachment-upload',
    templateUrl: './attachment-upload.component.html',
    styleUrls: ['./attachment-upload.component.scss']
})
export class AttachmentUploadComponent implements OnInit {

    @Input() showHeader: boolean = true;

    @Input() allowRemove: boolean = true;

    @Input() attachments: Array<AttachmentVo> = new Array();

    @Output() onAttachmentResults: EventEmitter<AttachmentVo[]> = new EventEmitter();

    @ViewChild('fileUpload')
    fileUpload: ElementRef;

    uploader: FileUploader;

    constructor(private appConfig: AppConfig, private appStorage: AppStorage, private dialogService: DialogsService) {
        let self = this;

        let uploader: FileUploader = new FileUploader(<FileUploaderOptions>{
            url: this.appConfig.getAttachmentBase() + "upload",
            method: "POST",
            autoUpload: true,
            maxFileSize: 20 * 1024 * 1024,
            queueLimit: 5,
            removeAfterUpload: true,
            headers: [
                {name: "token", value: this.appStorage.loginData.token}
            ],
            // authToken: this.appStorage.loginData.token,
            // authTokenHeader: this.appStorage.loginData.token,
            allowedFileType: ['application', 'image', 'video', 'audio', 'pdf', 'compress', 'doc', 'xls', 'ppt']
        });

        uploader.onWhenAddingFileFailed = this.whenAddingFileFailed.bind(this);
        uploader.onSuccessItem = this.successItem.bind(this);
        uploader.onAfterAddingFile = this.afterAddFile;
        uploader.onBuildItemForm = this.buildItemForm;
        uploader.onCompleteAll = function () {
            self.completeAll();
        };

        this.uploader = uploader;
    }

    ngOnInit() {
    }

    public fileSelect(): any {
        this.fileUpload.nativeElement.click();
    }

    fileAllUp(): any {
        this.uploader.uploadAll();
    }

    fileAllCancel(): any {
        this.uploader.cancelAll();
    }

    fileAllDelete(): any {
        this.uploader.clearQueue();
    }

    whenAddingFileFailed(item: FileLikeObject, filter: any, options: any): any {
        if (item.size > 20 * 1024 * 1024) {
            this.dialogService.alert('提示', '大小超过限制或者格式不被支持');
        }
    }

    selectedFileOnChanged(event) {
        // 这里是文件选择完成后的操作处理
    }

    buildItemForm(fileItem: FileItem, form: any): any {
        if (!!fileItem["realFileName"]) {
            form.append("fileName", fileItem["realFileName"]);
        }
    }

    afterAddFile(fileItem: FileItem): any {
        fileItem.withCredentials = false;
    }

    changeFileName(value: any, fileItem: FileItem) {
        fileItem["realFileName"] = value.target.value;
    }

    successItem(item: FileItem, response: string, status: number, headers: ParsedResponseHeaders): any {
        // 上传文件成功
        if (status == 200) {
            // 上传文件后获取服务器返回的数据
            let responseResult = JSON.parse(response);
            if (responseResult.result === SUCCESS) {
                let attachment: AttachmentVo = null;
                if (responseResult.data) {
                    attachment = responseResult.data[0];
                    if (ObjectUtils.isEmpty(this.attachments)) {
                        this.attachments = new Array<AttachmentVo>();
                    }
                    Utils.toggleInArray(attachment, this.attachments);
                }
            } else {
                this.dialogService.toast('error', '文件上传失败', responseResult.message);
            }

        } else {
            this.dialogService.toast('error', '文件上传错误', response);
        }

    }

    completeAll() {
        this.onAttachmentResults.emit(this.attachments);
    }

    removeAttachmentSubscription(event) {
        this.dialogService.confirm('提示', '确认要删除吗？').then(result => {
            if (result) {
                Utils.removeItemInArray(event, this.attachments);
                this.onAttachmentResults.emit(this.attachments);
            }
        });
    }

    clearAttachments() {
        if (this.attachments && this.attachments.length > 0) {
            this.attachments.splice(0, this.attachments.length);
        }
    }

}
