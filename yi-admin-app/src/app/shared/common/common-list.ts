import {ActivatedRoute, Router} from "@angular/router";
import {PageQuery} from "../../pages/models/page-query.model";
import {SUCCESS} from "../../pages/models/constants.model";
import {DialogsService} from "../../pages/components/dialogs/dialogs.service";
import {BaseService} from "../../services/base.service";

export class CommonList {

    pageQuery: PageQuery = new PageQuery();

    selectId: string = "0";

    anys: Array<any>


    constructor(public route: ActivatedRoute, public router: Router, public dialogService: DialogsService, public baseService: BaseService) {
    }


    getSelect(id, i) {
        this.selectId = id
        /*  let elementsByClassName: any = document.getElementsByName("select");
          elementsByClassName.item(i).checked = true*/
    }

    getDatas() {
        this.baseService.query(this.pageQuery).subscribe(response => {
            this.anys = response.content;
            this.pageQuery.covertResponses(response);
        });
    }

    pageChange(event) {
        this.pageQuery.page = event;
        this.getDatas();
    }

    edit() {
        if (this.selectId !== "0") {
            this.router.navigate(['../edit/' + this.selectId], {relativeTo: this.route});
        } else {
            this.dialogService.toast('error');
        }
    }

    view() {
        if (this.selectId !== "0") {
            this.router.navigate(['../view/' + this.selectId], {relativeTo: this.route});
        } else {
            this.dialogService.toast('error');
        }
    }

    remove() {
        this.dialogService.confirm('提示', '确认要删除吗？').then((result) => {
            if (result) {

                this.baseService.removeById(this.selectId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.pageQuery.requests.page = 1;
                        this.getDatas();
                        this.selectId = "0"
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

    clear() {
        this.pageQuery.clearFilter();
        let elementsByClassName: any = document.getElementsByClassName("filter");
        for (let elementsByClassNameKey in elementsByClassName) {
            elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
        }
        this.getDatas();

    }

}