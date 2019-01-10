import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DeptSimple} from "../../../../../models/domain/simple/dept-simple.model";
import {ObjectUtils} from "../../../../../../shared/utils/ObjectUtils";
import {DeptParentChildrenVo} from "../../../../../models/domain/vo/dept-parent-children-vo.model";
import {DeptService} from "../../../../../../services/dept.service";

@Component({
    selector: 'app-dept-breadcrumb-no-child',
    templateUrl: './dept-breadcrumb-no-child.component.html',
    styleUrls: ['./dept-breadcrumb-no-child.component.scss'],
    providers: [DeptService]
})
export class DeptBreadcrumbNoChildComponent implements OnInit {

    @Input() deptId = 0;

    @Output() onDeptChange: EventEmitter<DeptSimple> = new EventEmitter();

    deptParentChildren: DeptParentChildrenVo = new DeptParentChildrenVo;

    constructor(private deptService: DeptService) {
    }

    ngOnChanges(value) {
        if (value.deptId !== undefined && value.deptId.currentValue !== undefined) {
            this.loadDataByDeptId(this.deptId);
        }
    }

    ngOnInit() {
        this.loadDataByDeptId(this.deptId);
    }

    loadDataByDeptId(deptId) {
        if (ObjectUtils.isEmpty(deptId)) {
            return;
        }

        // this.deptService.getParentsAndChildren(deptId).subscribe(response => {
        //   if (response.result == SUCCESS) {
        //     this.deptParentChildren = response.data;
        //     this.onDeptChange.emit(this.deptParentChildren.dept);
        //   }
        // });
    }

}
