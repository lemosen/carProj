import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DeptSimple} from "../../../../../models/domain/simple/dept-simple.model";
import {AppStorage} from "../../../../../configs/app-storage";
import {DeptParentChildrenVo} from "../../../../../models/domain/vo/dept-parent-children-vo.model";
import {DeptService} from "../../../../../../services/dept.service";

@Component({
    selector: 'app-dept-breadcrumb-show-child',
    templateUrl: './dept-breadcrumb-show-child.component.html',
    styleUrls: ['./dept-breadcrumb-show-child.component.scss'],
    providers: [DeptService]
})
export class DeptBreadcrumbShowChildComponent implements OnInit {

    @Input() showChild: boolean = true;

    @Output() onDeptChange: EventEmitter<DeptSimple> = new EventEmitter();

    dept: DeptParentChildrenVo = new DeptParentChildrenVo;

    constructor(private deptService: DeptService, private appStorage: AppStorage) {
    }

    ngOnChanges(value) {
        if (value.deptId.currentValue !== undefined) {
            this.loadDataByDeptId(value.deptId.currentValue);
        }
    }

    ngOnInit() {
        this.loadDataByDeptId(this.appStorage.loginData.workDepts[0].id);
    }

    loadDataByDeptId(deptId) {
        // this.deptService.getParentsAndChildren(deptId).subscribe(response => {
        //   this.dept = response.data;
        //   this.onDeptChange.emit(this.dept.dept);
        // });
    }


}
