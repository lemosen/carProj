import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {RegionService} from '../../../services/region.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-region',
    templateUrl: './list-region.component.html',
    styleUrls: ['./list-region.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRegionComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private regionService: RegionService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, regionService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            province: [null],
            city: [null],
            state: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            id: null,
            guid: null,
            province: null,
            city: null,
            state: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.province != null) {
            pageQuery.addOnlyFilter('province', searchObj.province, 'contains');
        }
        if (searchObj.city != null) {
            pageQuery.addOnlyFilter('city', searchObj.city, 'contains');
        }
        return pageQuery;
    }
    selectMenu: number = 0
    onItemClick(i) {
        this.pageQuery.clearFilter();
        let elementsByClassName: any = document.getElementsByClassName("filter");
        for (let elementsByClassNameKey in elementsByClassName) {
            elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
        }
        this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('state');
        this.getDatas();
        this.selectMenu = i;
    }


    menus = [
        {name: "全部地区", code: 0, value: ""},
        {name: "启用", code: 1, value: 0},
        {name: "禁用", code: 2, value: 1},
    ];


    disable(recommendId){
        this.dialogService.confirm('提示', '确认禁用吗？').then(result => {
            if (result) {
                this.regionService.banKai(recommendId).subscribe(response => {
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
        })
    }


    enable(recommendId){
        this.dialogService.confirm('提示', '确认启用吗？').then(result => {
            if (result) {
                this.regionService.banKai(recommendId).subscribe(response => {
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
        })
    }


}
