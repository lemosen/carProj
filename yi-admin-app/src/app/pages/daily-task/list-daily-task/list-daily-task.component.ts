


import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {DailyTaskService} from '../../../services/daily-task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-daily-task',
    templateUrl: './list-daily-task.component.html',
    styleUrls: ['./list-daily-task.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListDailyTaskComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private dailyTaskService: DailyTaskService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, dailyTaskService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
        id:[null],
        guid:[null],
        taskName:[null],
        growthValue:[null],
        state:[null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    updateGrowthValue(id,growthValue,thisgrowthValue){
                if(growthValue==""){
                    this.getDatas();
                    return
                }

                if(thisgrowthValue==growthValue){
                    return
                }
                this.dailyTaskService.updateGrowthValue(id,growthValue).subscribe(response => {
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

    updateState(id){
        this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                this.dailyTaskService.updateStates(id).subscribe(response => {
                    if (response.result == SUCCESS) {

                      /*  this.router.navigate(['/pages/daily-task/list']);*/
                        this.getDatas();
                        this.dialogService.toast();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        }, () => {
        });

    }

    clearSearch() {
        this.searchForm.reset({
        id:null,
        guid:null,
        taskName:null,
        growthValue:null,
        state:null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    onSubmit(){

    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.provinceId) {
     pageQuery.addOnlyFilter('id', searchObj.id, 'eq');
     pageQuery.addOnlyFilter('guid', searchObj.guid, 'eq');
     pageQuery.addOnlyFilter('taskName', searchObj.taskName, 'eq');
     pageQuery.addOnlyFilter('growthValue', searchObj.growthValue, 'eq');
     pageQuery.addOnlyFilter('state', searchObj.state, 'eq');

        }
        return pageQuery;
    }


}
