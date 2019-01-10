import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NodeEvent} from "ng2-tree";
import {DeptService} from "../../../services/dept.service";
import {DialogsService} from "../dialogs/dialogs.service";

// import {DeptTreeNode} from '../../system/dept/dept.tree.model';

@Component({
    selector: 'app-dept-tree',
    templateUrl: './dept-tree.component.html',
    styleUrls: ['./dept-tree.component.scss']
})
export class DeptTreeComponent implements OnInit {
    // deptDatas: DeptTreeNode;

    // settings: {
    //   'static': true,
    //   'rightMenu': true,
    //   'leftMenu': true,
    //   'cssClasses': {
    //     'expanded': 'fa fa-caret-down fa-lg',
    //     'collapsed': 'fa fa-caret-right fa-lg',
    //     'leaf': 'fa fa-lg',
    //     'empty': 'fa fa-caret-right disabled'
    //   },
    //   'templates': {
    //     'node': '<i class="fa fa-folder-o fa-lg"></i>',
    //     'leaf': '<i class="fa fa-file-o fa-lg"></i>',
    //     'leftMenu': '<i class="fa fa-navicon fa-lg"></i>'
    //   },
    //   'menuItems': [
    //     { action: NodeMenuItemAction.Custom, name: 'userCount', cssClass: 'fa fa-arrow-right' }
    //     ]
    // };

    @Output() deptSelected: EventEmitter<{ deptId: number, deptName: string }> = new EventEmitter();
    private nodeSettings: any = {
        static: true
    };

    constructor(private deptService: DeptService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.getTree();
    }

    getTree() {
        // this.deptService.tree().subscribe(response => {
        //   this.deptDatas = this.formartTreeData(response.data);
        // });
    }

    handleSelected(e: NodeEvent): void {
        // this.deptSelected.emit({
        //   deptId: Number(e.node.id),
        //   deptName: e.node.value
        // });
    }

    formartTreeData(result) {
        if (!result) return;

        // const node = new DeptTreeNode();
        // node.id = result.deptId;
        // node.value = result.deptName;
        // node.userCount = result.userCount;
        // node.settings = this.nodeSettings;
        //
        // const childrenNodes = result.children;
        // if (childrenNodes && childrenNodes.length > 0) {
        //   node.children = [];
        //
        //   for (let index = 0; index < childrenNodes.length; ++index) {
        //     node.children[index] = this.formartTreeData(childrenNodes[index]);
        //   }
        // }
        // return node;
    }

}
