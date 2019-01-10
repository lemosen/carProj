import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {DeptSimple} from "../../../../models/domain/simple/dept-simple.model";
import {Utils} from "../../../../../shared/utils/Utils";
import {ObjectChooseQuery} from "../../../../models/domain/object-choose-query";
import {ObjectUtils} from "../../../../../shared/utils/ObjectUtils";
import {DeptService} from "../../../../../services/dept.service";
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-dept-multiple-dialog',
    templateUrl: './dept-multiple-dialog.component.html',
    styleUrls: ['./dept-multiple-dialog.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [DeptService]
})
export class DeptMultipleDialogComponent implements OnInit {

    public configData = {
        title: '选择',
        confirmText: '确认',
        cancelText: '取消'
    };
    chooseQuery: ObjectChooseQuery = new ObjectChooseQuery;
    result: any;
    currentDept: DeptSimple;
    chooseDepts: Array<DeptSimple>;
    deptSimples: Array<DeptSimple>;

    constructor(public bsModalRef: NgbModalRef, private deptService: DeptService) {
        this.chooseDepts = new Array<DeptSimple>();
    }

    private _conditionFilters: any = null;

    get conditionFilters(): any {
        return this._conditionFilters;
    }

    set conditionFilters(value: any) {
        this._conditionFilters = value;
        if (value) {
            value.map(data => {
                this.chooseDepts.push(data)
            });
        }
    }

    ngOnInit() {
        this.queryObjectsByConditions();
    }

    searchData(event) {
        this.chooseQuery.queryText = event;
        this.queryObjectsByConditions();
    }

    queryObjectsByConditions() {
        this.deptService.queryObjectsByConditions(this.chooseQuery).subscribe(response => {
            this.deptSimples = response.data;
        });
    }

    onDeptChange(result) {
        this.currentDept = result;
        if (result) {
            this.chooseQuery.deptId = this.currentDept.id;
        } else {
            this.chooseQuery.deptId = 0;
        }
        this.queryObjectsByConditions();
    }

    onChooseResult(event) {
        this.chooseDepts.push(event);
        this.chooseDepts = Utils.toggleInArrayByField(this.chooseDepts, 'deptId');
    }

    onChooseChildLevel(event) {
        this.chooseQuery.deptId = event.deptId;
        this.queryObjectsByConditions();
    }

    getText(obj: any, fieldName) {
        return obj[fieldName];
    }

    removeChoose(event) {
        Utils.removeItemInArray(event, this.chooseDepts);
    }

    public onClose() {
        this.bsModalRef.close(false);
    }

    public onAccept() {
        if (ObjectUtils.isNotEmpty(this.chooseDepts)) {
            this.bsModalRef.close(this.chooseDepts);
        }
    }

}
