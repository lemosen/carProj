import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Ng2TreeSettings, NodeEvent, NodeMenuItemAction, TreeModel} from "ng2-tree";
import {TreeModelSettings} from "ng2-tree/src/tree.types";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {RescService} from "../../../services/resc.service";

@Component({
    selector: 'app-jurisdiction',
    templateUrl: './jurisdiction.component.html',
    styleUrls: ['./jurisdiction.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class TreeComponent implements OnInit {

    @Input()
    title: string = 'name'
    @Input()
    id: string = 'id';

    @Input()
    level: number = 2;

    /*    @Input()
        sureButton="保存";*/

    @Output()
    result: EventEmitter<any> = new EventEmitter();
    treeSettings: Ng2TreeSettings = {
        rootIsVisible: true,
        showCheckboxes: true
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

    _tree: TreeModel

    @ViewChild('jurisdiction') jurisdiction
    /*@ViewChild('content') content*/
    @Input()
    _data = [];

    constructor(private modalService: NgbModal, public rescService: RescService) {
    }

    /*
        rescService: RescService
        getAllCategory() {
            return this.getByParams("getAll")
        }
    */
    ngOnInit() {
    }

    /**
     * 组件加载完后
     */
    ngAfterViewInit() {
        this.rescService.getAllResc().subscribe(response => {
            this.formatData(response.data, this.level)
        });

    }

    formatData(data: any, level: number) {
        this._tree = {
            value: '全部权限',
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
        this._tree.children.forEach(e => {
            data.filter(e => e.parent && e.parent.id != 0).forEach(e1 => {
                if (e.id == e1.parent.id) {
                    let child = {
                        value: e1[this.title], id: e1[this.id], settings: {}
                    }
                    if (this._data.filter(e2 => e2.id == e1.id).length != 0) {
                        child.settings = this._settings;
                    }
                    e.children.push(child)
                }
            })
            // console.log(e.children.filter(e1 => e1.settings.checked).length == e.children.length);
            if (e.children.filter(e1 => e1.settings.checked).length == e.children.length && e.children.length != 0) {
                e.settings = this._settings
            }
        })
    }

    /*handleSelected(e: NodeEvent): void {
        this.result.emit({
            id: Number(e.node.id),
            name: e.node.value
        })
    }
*/
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

    // handleSelected(e: NodeEvent): void {
    //
    //     console.log(NodeEvent)
    //         if(e.node.id==this._tree.value){
    //             this._tree.children.forEach(e1=>{
    //                 e1.children.forEach(e2 => {
    //                     if (this.checkSelects.filter(e3 => e3.id == e2.id).length == 0) {
    //                         this.checkSelects.push(e2);
    //                     }
    //                 })
    //             })
    //
    //         }else {
    //
    //             let filter = this._tree.children.filter(e2 => e2.id == e.node.id );
    //             console.log(21)
    //             console.log(filter)
    //             if (filter.length == 0) {
    //                 if (this.checkSelects.filter(e1 => e1.id == e.node.id).length == 0) {
    //                     this.checkSelects.push({
    //                         id: Number(e.node.id),
    //                         value: e.node.value
    //                     });
    //                     console.log(this.checkSelects)
    //                 }
    //             } else {
    //                 filter[0].children.forEach(e1 => {
    //                     if (this.checkSelects.filter(e2 => e2.id == e1.id).length == 0) {
    //                         this.checkSelects.push(e1);
    //                     }
    //                 })
    //             }
    //         }
    //      console.log(this.checkSelects)
    //
    //      this.result.emit(this.checkSelects);
    // }
    /*cancel(e: NodeEvent): void {
        this.result.emit({
            id: Number(e.node.id),
            name: e.node.value
        })
    }*/


    /**
     * 打开modal
     * @param content
     */
    /*open(content) {
        this.modalService.open(this.content,).result.then((result) => {
            // this.result.emit(this.select);
        }, (reason) => {

        });
    }*/

}
