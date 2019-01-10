


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AttachmentService} from '../../../services/attachment.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-attachment',
    templateUrl: './list-attachment.component.html',
    styleUrls: ['./list-attachment.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAttachmentComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private attachmentService: AttachmentService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, attachmentService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        attachId:[null],
        objectType:[null],
        filePath:[null],
        objectId:[null],
        objectPath:[null],
        fileName:[null],
        fileExt:[null],
        fileType:[null],
        fileSize:[null],
        fsGuid:[null],
        createTime:[null],
        userId:[null],
        userName:[null],
        description:[null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
        attachId:null,
        objectType:null,
        filePath:null,
        objectId:null,
        objectPath:null,
        fileName:null,
        fileExt:null,
        fileType:null,
        fileSize:null,
        fsGuid:null,
        createTime:null,
        userId:null,
        userName:null,
        description:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.provinceId) {
     pageQuery.addOnlyFilter('attachId', searchObj.attachId, 'eq');
     pageQuery.addOnlyFilter('objectType', searchObj.objectType, 'eq');
     pageQuery.addOnlyFilter('filePath', searchObj.filePath, 'eq');
     pageQuery.addOnlyFilter('objectId', searchObj.objectId, 'eq');
     pageQuery.addOnlyFilter('objectPath', searchObj.objectPath, 'eq');
     pageQuery.addOnlyFilter('fileName', searchObj.fileName, 'eq');
     pageQuery.addOnlyFilter('fileExt', searchObj.fileExt, 'eq');
     pageQuery.addOnlyFilter('fileType', searchObj.fileType, 'eq');
     pageQuery.addOnlyFilter('fileSize', searchObj.fileSize, 'eq');
     pageQuery.addOnlyFilter('fsGuid', searchObj.fsGuid, 'eq');
     pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
     pageQuery.addOnlyFilter('userId', searchObj.userId, 'eq');
     pageQuery.addOnlyFilter('userName', searchObj.userName, 'eq');
     pageQuery.addOnlyFilter('description', searchObj.description, 'eq');

        }
        return pageQuery;
    }


}
