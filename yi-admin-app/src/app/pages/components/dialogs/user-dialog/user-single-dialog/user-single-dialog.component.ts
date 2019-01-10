import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UserSimple} from "../../../../models/domain/simple/user-simple.model";
import {DeptSimple} from "../../../../models/domain/simple/dept-simple.model";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {ObjectChooseQuery} from "../../../../models/domain/object-choose-query";
import {AppStorage} from "../../../../configs/app-storage";
import {UserService} from "../../../../../services/user.service";
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-user-single-dialog',
    templateUrl: './user-single-dialog.component.html',
    styleUrls: ['./user-single-dialog.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [UserService]
})
export class UserSingleDialogComponent implements OnInit {

    public configData = {
        title: '选择',
        confirmText: '确认',
        cancelText: '取消'
    };
    chooseQuery: ObjectChooseQuery = new ObjectChooseQuery;
    closeResultBehavior = new BehaviorSubject<any>(null);
    result: any;
    currentDept: DeptSimple;
    chooseUser: UserSimple;
    userSimples: Array<UserSimple>;

    constructor(public bsModalRef: NgbModalRef, private userService: UserService, private appStorage: AppStorage) {
    }

    private _conditionFilters: any = null;

    get conditionFilters(): any {
        return this._conditionFilters;
    }

    set conditionFilters(value: any) {
        this._conditionFilters = value;
        if (value) {
            this.chooseUser = value;
        }
    }

    ngOnInit() {
        this.chooseQuery.deptId = this.appStorage.loginData.workDepts[0].id;
        this.queryObjectsByConditions();
    }

    searchData(event) {
        this.chooseQuery.queryText = event;
        this.queryObjectsByConditions();
    }

    queryObjectsByConditions() {
        this.userService.queryObjectsByConditions(this.chooseQuery).subscribe(response => {
            this.userSimples = response.data;
        });
    }

    onDeptChange(result) {
        this.currentDept = result;
        this.chooseQuery.deptId = this.currentDept.id;
        this.queryObjectsByConditions();
    }

    onChooseResult(event) {
        this.chooseUser = event;
    }

    getText(obj: any, fieldName) {
        return obj[fieldName];
    }

    removeChoose(event) {
        this.chooseUser = null;
    }

    public onClose() {
        this.bsModalRef.close();
    }

    public onAccept() {
        this.bsModalRef.close(this.chooseUser);
    }

}
