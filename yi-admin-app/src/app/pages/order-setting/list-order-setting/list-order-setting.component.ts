import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {OrderSettingService} from '../../../services/order-setting.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-order-setting',
    templateUrl: './list-order-setting.component.html',
    styleUrls: ['./list-order-setting.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListOrderSettingComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private orderSettingService: OrderSettingService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, orderSettingService);
        this.buildForm();
    }

    setTypes = [{
        code: 1,
        title: "待付款",
    }, {
        code: 2,
        title: "待评价",
    }]

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            setType: [null],
            timeout: [null],
            day: [null],
            hour: [null],
            minute: [null],
            createTime: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            setType: null,
            timeout: null,
            day: null,
            hour: null,
            minute: null,
            createTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.setType != null) {
            pageQuery.addOnlyFilter('setType', searchObj.setType, 'eq');
        }
        return pageQuery;
    }

    updateMinuteValue(id,updateMinute,minute){
        if(updateMinute==minute){
            return
        }
        this.orderSettingService.updateMinuteValue(id,updateMinute).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dialogService.toast();
                this.getDatas();
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    updateDayValue(id,updateDay,day){
        if(updateDay==day){
            return
        }
        this.orderSettingService.updateDayValue(id,updateDay).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dialogService.toast();
                this.getDatas();
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

}
