


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {RecommendService} from '../../../services/recommend.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-recommend',
    templateUrl: './list-recommend.component.html',
    styleUrls: ['./list-recommend.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRecommendComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private recommendService: RecommendService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, recommendService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    menus = [
        {name: "全部商品", code: 0, value: ""},
        {name: "启用", code: 1, value: 0},
        {name: "禁用", code: 2, value: 1},
    ];

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

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        title:[null],
        imgPath:[null],
        state:[null],
        createTime:[null],
        deleted:[null],
        delTime:[null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
        id:null,
        guid:null,
        title:null,
        imgPath:null,
        state:null,
        createTime:null,
        deleted:null,
        delTime:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.title!=null) {

           pageQuery.addOnlyFilter('title', searchObj.title, 'contains');

        }
        return pageQuery;
    }
   /* banKai(recommendId){
        this.dialogService.confirm('提示', '确认要提交吗？').then(result => {
            this.recommendService.banKai(recommendId).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.recommend = response.data;
                } else {
                    this.dialogService.alert('请求失败', response.message);
                }
            }, error => {
                this.dialogService.alert('请求错误', error.message);
            });
        })
    }*/
    disable(recommendId){
        this.dialogService.confirm('提示', '确认禁用吗？').then(result => {
            if (result) {
            this.recommendService.updateStateDisable(recommendId).subscribe(response => {
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
                this.recommendService.updateStateEnable(recommendId).subscribe(response => {
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
