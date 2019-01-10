import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TabService} from "./tab.service";

@Component({
    selector: 'app-tab-item',
    templateUrl: './tab.component.html',
    styleUrls: ['./tab.component.scss']
})
export class TabComponent implements OnInit {

    // @Input() task: TaskListVo;
    // 要显示的选项卡
    @Input() tabs: Array<any>;
    // 选项卡对应的显示div
    @Input() tabIds: Array<any>;
    @Input() taskMenuButton: Array<any> = [{
        text: '查看任务',
        value: 'view',
        click: (task, behavior) => {
            //this.linkObjectsService.setTaskMenuButtonBehavior(task,behavior);
        }
    }, {
        text: '修改任务',
        value: 'edit',
        click: (task, behavior) => {
            // this.linkObjectsService.setTaskMenuButtonBehavior(task,behavior);
        }
    }, {
        text: '删除任务',
        value: 'remove',
        click: (task, behavior) => {
            // this.linkObjectsService.setTaskMenuButtonBehavior(task,behavior);
        }
    }];
    comments;

    constructor(private tabService: TabService, private router: Router) {
    }

    private _showChildType: string = 'none';

    get showChildType(): string {
        return this._showChildType;
    }

    set showChildType(value: string) {
        if (this._showChildType === value) {
            this._showChildType = 'none';
        } else {
            this._showChildType = value;
        }
    }

    dj(index) {
        alert(index);
    }

    ngOnInit() {

    }

    goToMonitor(id) {
        this.router.navigate(['/task/taskMonitor', id]);
    }

}
