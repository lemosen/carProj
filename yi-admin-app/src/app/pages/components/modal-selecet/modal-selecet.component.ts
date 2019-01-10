import {Component, EventEmitter, Input, Output, ViewEncapsulation} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PageQuery} from "../../models/page-query.model";
import {BaseService} from "../../../services/base.service";
import {number} from "ng2-validation/dist/number";

@Component({
    selector: 'app-modal-selecet',
    templateUrl: './modal-selecet.component.html',
    styleUrls: ['./modal-selecet.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: []
})
export class ModalSelecetComponent {

    /**
     * 标题
     * @type {string}
     */
    @Input()
    title: string = '请选择';

    /**
     * 打开modal按钮
     * @type {string}
     */
    @Input()
    openButton: string = "请选择";

    /**
     * 确定按钮
     * @type {string}
     */
    @Input()
    sureButton: string = '保存';

    /**
     * 已选对象 多选传数组 require
     * @type {number}
     */
    @Input()
    select;

    /**
     * 查询service require
     * @type {null}
     */
    @Input()
    baseService: BaseService;

    /**
     * 显示字段 require
     * @type {any[]}
     */
    @Input()
    showCol = [];

    /**
     * 是否多选 默认单选
     * @type {boolean}
     */
    @Input()
    isMulti = false;

    /**
     * 按钮上的显示值
     * @type {string}
     */
    @Input()
    resultName = '请选择';

    @Output()
    result = new EventEmitter

    /**
     * 显示对象s
     * @type {any[]}
     */
    private _anys = [];

    /**
     * 显示名
     * @type {string}
     */
    private _showName = '';

    @Input()
    private pageQuery: PageQuery = new PageQuery()


    constructor(private modalService: NgbModal) {
    }

    pageChange(event) {
        this.pageQuery.page = event;
        this.getData();
    }

    /**
     * 组件加载完后
     */
    ngAfterViewInit() {
        this.getData();
        this.showCol.forEach(e => {
            if (e.isShow) {
                this._showName = e.name
            }
        })
    }


    getData() {
        this.baseService.query(this.pageQuery).subscribe(response => {
            this._anys = response.content;
            this.pageQuery.covertResponses(response);
        });
    }

    /**
     * 点击选中
     * @param item
     */
    getSelect(item, id) {
        if (item != null) {
            if (this.isMulti) {
                if (this.getMultCheck(item)) {
                    this.select.splice(this.select.indexOf(this.select.filter(e => e.id == id)[0]), 1);
                } else {
                    this.select.push(item)
                }
                this.resultName = ""
                this.select.forEach((e, index) => {
                    this.resultName += e[this._showName];
                    if (this.select.length != index + 1) {
                        this.resultName += ","
                    }
                })
            } else {
                this.select = item
                this.resultName = item[this._showName]
            }
        } else {
            console.error("无初始值 没有也传个空集合过来")
        }
    }

    /**
     * 多选是否选中
     * @param item
     * @returns {boolean}
     */
    getMultCheck(item) {
        if (!this.select.length) {
            return false
        }
        let filter = this.select.filter(e => e.id == item.id);
        return filter.length != 0
    }

    /**
     * 打开modal
     * @param content
     */
    open(content) {
        this.modalService.open(content,).result.then((result) => {
            this.result.emit(this.select);
        }, (reason) => {

        });
    }

    reset() {
        this.resultName = '请选择';
    }

}
