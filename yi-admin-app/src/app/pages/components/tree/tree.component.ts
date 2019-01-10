import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Ng2TreeSettings, NodeEvent, NodeMenuItemAction, TreeModel} from "ng2-tree";
import {TreeModelSettings} from "ng2-tree/src/tree.types";
import {CategoryService} from "../../../services/category.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-tree',
    templateUrl: './tree.component.html',
    styleUrls: ['./tree.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class TreeComponent implements OnInit {

    @Input()
    title: string = 'categoryName'
    @Input()
    id: string = 'id';

    @Input()
    level: number = 2;

    @Input()
    isSecond = true;

    @Input()
    isCheckBox = false;

    @Output()
    result: EventEmitter<any> = new EventEmitter();
    treeSettings: Ng2TreeSettings = {
        rootIsVisible: true,
        //复选的设置
    }
    _settings: TreeModelSettings = {
        'static': true,
        'rightMenu': true,
        'leftMenu': true,
        isCollapsedOnInit: true,
        'cssClasses': {
            'expanded': 'fa fa-caret-down fa-lg',
            'collapsed': 'fa fa-caret-right fa-lg',
            'leaf': 'fa fa-lg',
            'empty': 'fa fa-caret-right disabled'
        },
        'templates': {
            'node': '<i class="fa fa-folder-o fa-lg"></i>',
            'leaf': '<i class="fa fa-file-o fa-lg"></i>',
            'leftMenu': '<i class="fa fa-navicon fa-lg"></i>'
        },
        'menuItems': [
            {action: NodeMenuItemAction.Custom, name: 'Foo', cssClass: 'fa fa-arrow-right'},
            {action: NodeMenuItemAction.Custom, name: 'Bar', cssClass: 'fa fa-arrow-right'},
            {action: NodeMenuItemAction.Custom, name: 'Baz', cssClass: 'fa fa-arrow-right'}
        ],
    }
    _check_settings: TreeModelSettings = {
        'static': true,
        'rightMenu': true,
        'leftMenu': true,
        isCollapsedOnInit: true,
        'cssClasses': {
            'expanded': 'fa fa-caret-down fa-lg',
            'collapsed': 'fa fa-caret-right fa-lg',
            'leaf': 'fa fa-lg',
            'empty': 'fa fa-caret-right disabled'
        },
        'templates': {
            'node': '<i class="fa fa-folder-o fa-lg"></i>',
            'leaf': '<i class="fa fa-file-o fa-lg"></i>',
            'leftMenu': '<i class="fa fa-navicon fa-lg"></i>'
        },
        'menuItems': [
            {action: NodeMenuItemAction.Custom, name: 'Foo', cssClass: 'fa fa-arrow-right'},
            {action: NodeMenuItemAction.Custom, name: 'Bar', cssClass: 'fa fa-arrow-right'},
            {action: NodeMenuItemAction.Custom, name: 'Baz', cssClass: 'fa fa-arrow-right'}
        ],
        //选上的设置(判断是否被选中)
        checked: true
    }


    _tree: TreeModel

    @ViewChild('appTree') appTree
    @Input()
    data = [];

    constructor(private modalService: NgbModal, public categoryService: CategoryService) {
    }

    ngOnInit() {
    }

    /**
     * 组件加载完后
     */
    ngAfterViewInit() {
        this.categoryService.getAllCategory().subscribe(response => {

            this.formatData(response.data, this.level)
        });

    }

    formatData(data: any, level: number) {
        this.treeSettings.showCheckboxes = this.isCheckBox
        this._tree = {
            value: '所有一级分类',
            id: 0,
            children: [],
            settings: this._settings
        };

        //constructor first
        this._tree.children = data.filter(e => !e.parent || e.parent.id == 0 && e.deleted == 0).map(e => {
            return {
                value: e[this.title], id: e[this.id], children: [], settings: this._settings
            };
        })
        //constructor second
        if (this.isSecond) {
            this._tree.children.forEach(e => {
                data.filter(e => e.parent && e.parent.id != 0).forEach(e1 => {
                    if (e.id == e1.parent.id) {
                        let child = {
                            value: e1[this.title], id: e1[this.id], settings: {}
                        }
                        if (this.data.filter(e2 => e2.id == e1.id).length != 0) {
                            child.settings = this._check_settings;
                        }
                        // console.log(child);
                        e.children.push(child)
                    }
                })
                // console.log(e.children.filter(e1 => e1.settings.checked).length == e.children.length);
                if (e.children.filter(e1 => e1.settings.checked).length == e.children.length && e.children.length != 0) {
                    e.settings = this._check_settings
                }
            })
        }
    }

    checkSelects = [];

    handleChecked(e: NodeEvent, isCheck: boolean): void {
        if (isCheck) {
            if (e.node.id == this._tree.value) {
                this._tree.children.forEach(e1 => {
                    e1.children.forEach(e2 => {
                        if (this.checkSelects.filter(e3 => e3.id == e2.id).length == 0) {
                            this.checkSelects.push(e2);
                        }
                    })
                })
            } else {
                let filter = this._tree.children.filter(e2 => e2.id == e.node.id);
                if (filter.length == 0) {
                    if (this.checkSelects.filter(e1 => e1.id == e.node.id).length == 0) {
                        this.checkSelects.push({
                            id: Number(e.node.id),
                            value: e.node.value
                        });
                    }
                } else {
                    filter[0].children.forEach(e1 => {
                        if (this.checkSelects.filter(e2 => e2.id == e1.id).length == 0) {
                            this.checkSelects.push(e1);
                        }
                    })
                }
            }
        } else {
            if (e.node.id == this._tree.value) {
                this.checkSelects = []
            } else {
                let filter = this._tree.children.filter(e2 => e2.id == e.node.id);
                if (filter.length == 0) {
                    let filter1 = this.checkSelects.filter(e1 => e1.id == e.node.id);
                    if (filter1.length == 1) {
                        this.checkSelects.splice(this.checkSelects.indexOf(filter1), 1);
                    }
                } else {
                    filter[0].children.forEach(e1 => {
                        if (this.checkSelects.filter(e2 => e2.id == e1.id).length == 1) {
                            this.checkSelects.splice(this.checkSelects.indexOf(e1), 1);
                        }
                    })
                }
            }
        }
        console.log(this.checkSelects)
        this.result.emit(this.checkSelects);
    }

    handleSelected(e: NodeEvent): void {
        this.result.emit({
            id: Number(e.node.id),
            name: e.node.value
        })
    }

    handle(e: NodeEvent): void {
        this.result.emit({
            id: Number(e.node.id),
            name: e.node.value
        })
    }


}
