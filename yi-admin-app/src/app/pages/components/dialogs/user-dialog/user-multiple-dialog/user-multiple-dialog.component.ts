import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UserSimple} from "../../../../models/domain/simple/user-simple.model";
import {Utils} from "../../../../../shared/utils/Utils";
import {DeptSimple} from "../../../../models/domain/simple/dept-simple.model";
import {ObjectChooseQuery} from "../../../../models/domain/object-choose-query";
import {AppStorage} from "../../../../configs/app-storage";
import {ObjectUtils} from "../../../../../shared/utils/ObjectUtils";
import {UserService} from "../../../../../services/user.service";
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-user-multiple-dialog',
    templateUrl: './user-multiple-dialog.component.html',
    styleUrls: ['./user-multiple-dialog.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [UserService]
})
export class UserMultipleDialogComponent implements OnInit {

    public configData = {
        title: '选择',
        confirmText: '确认',
        cancelText: '取消'
    };
    chooseQuery: ObjectChooseQuery = new ObjectChooseQuery;
    result: any;
    currentDept: DeptSimple;
    chooseUsers: Array<UserSimple>;
    userSimples: Array<UserSimple>;

    constructor(public bsModalRef: NgbModalRef, private userService: UserService, private appStorage: AppStorage) {
        this.chooseUsers = new Array<UserSimple>();
    }

    private _conditionFilters: any = null;

    get conditionFilters(): any {
        return this._conditionFilters;
    }

    set conditionFilters(value: any) {
        this._conditionFilters = value;
        if (value) {
            value.map(data => {
                this.chooseUsers.push(data)
            });
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
        this.chooseUsers.push(event);
        this.chooseUsers = Utils.toggleInArrayByField(this.chooseUsers, 'userId');
    }

    getText(obj: any, fieldName) {
        return obj[fieldName];
    }

    removeChoose(event) {
        Utils.removeItemInArray(event, this.chooseUsers);
    }

    public onClose() {
        this.bsModalRef.close();
    }

    public onAccept() {
        if (ObjectUtils.isNotEmpty(this.chooseUsers)) {
            this.bsModalRef.close(this.chooseUsers);
        }
    }

}
