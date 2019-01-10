import {Component, OnInit} from '@angular/core';
import {DeptSimple} from "../../../../models/domain/simple/dept-simple.model";
import {ObjectChooseQuery} from "../../../../models/domain/object-choose-query";
import {DeptService} from "../../../../../services/dept.service";
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-dept-single-dialog',
    templateUrl: './dept-single-dialog.component.html',
    styleUrls: ['./dept-single-dialog.component.scss'],
    providers: [DeptService]
})
export class DeptSingleDialogComponent implements OnInit {

    public configData = {
        title: '选择',
        confirmText: '确认',
        cancelText: '取消',
    };
    chooseQuery: ObjectChooseQuery = new ObjectChooseQuery;
    result: any;
    currentDept: DeptSimple;
    chooseDept: DeptSimple;
    deptSimples: Array<DeptSimple>;

    constructor(public bsModalRef: NgbModalRef, private deptService: DeptService) {

    }

    private _conditionFilters: any = null;

    get conditionFilters(): any {
        return this._conditionFilters;
    }

    set conditionFilters(value: any) {
        this._conditionFilters = value;
        if (value) {
            this.chooseDept = value;
            this.chooseQuery.deptId = value.deptId;
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
        this.chooseDept = event;
    }

    onChooseChildLevel(event) {
        this.chooseQuery.deptId = event.deptId;
        this.queryObjectsByConditions();
    }

    getText(obj: any, fieldName) {
        return obj[fieldName];
    }

    removeChoose(event) {
        this.chooseDept = null;
    }

    public onClose() {
        this.bsModalRef.close();
    }

    public onAccept() {
        this.bsModalRef.close(this.chooseDept);
    }

}
