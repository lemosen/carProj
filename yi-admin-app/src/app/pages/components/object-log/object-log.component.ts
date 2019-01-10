import {Component, Input, OnInit} from '@angular/core';
import {PageQuery} from "../../models/page-query.model";
import {ObjectLogService} from "./object-log.service";
import {AppStorage} from "../../configs/app-storage";
import {ObjectInfo} from "../../models/domain/object-info.model";
import {BusinessLogVo} from "../../models/domain/vo/business-log-vo.model";

@Component({
    selector: 'app-object-log',
    templateUrl: './object-log.component.html',
    styleUrls: ['./object-log.component.scss'],
    providers: [ObjectLogService]
})
export class ObjectLogComponent implements OnInit {

    @Input() showRecordNum: boolean = false;

    @Input() objectInfo: ObjectInfo;

    pageQuery: PageQuery = new PageQuery();

    objectLogs: Array<BusinessLogVo>;

    constructor(private objectLogService: ObjectLogService, private appStorage: AppStorage) {
    }

    ngOnChanges(value) {
        if (value.objectInfo !== undefined && value.objectInfo.currentValue.objectType !== undefined && value.objectInfo.currentValue.objectId !== undefined) {
            this.getDatas();
        }
    }

    ngOnInit() {
    }

    getDatas() {
        const loginData = this.appStorage.loginData;
        this.pageQuery.clearFilter();
        this.pageQuery.addFilter("objectType", this.objectInfo.objectType, 'eq');
        this.pageQuery.addFilter("objectId", this.objectInfo.objectId, 'eq');
        this.objectLogService.query(this.pageQuery).subscribe(response => {
            this.objectLogs = response.content;
            this.pageQuery.covertResponses(response);
        });
    }

    objectLogPageChange(event) {
        this.pageQuery.page = event.page;
        this.getDatas();
    }
}
