import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {RescService} from '../../../services/resc.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";
import {Resc} from "../../models/original/resc.model";

@Component({
    selector: 'app-list-resc',
    templateUrl: './list-resc.component.html',
    styleUrls: ['./list-resc.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListRescComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    @ViewChild('tree') tree

    resc: Resc;

    constructor(public route: ActivatedRoute, public router: Router, private rescService: RescService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, rescService);
        this.buildForm();
    }


    open() {
        this.tree.open();
    }

    setParentResc(event) {
      /*  if(event.id==0){
            return
        }
          const*/
    /*    if (event.id != 0) {
            this.rescService.getById(event.id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.resc = response.data;
                } else {
                    this.dialogService.alert('请求失败', response.message);
                }
            }, error => {
                this.dialogService.alert('请求错误', error.message);
            });
        }*/


        // this.categoryChoose=event.name;
    }

    ngOnInit() {
        this.getDatas();
    }


    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            parentId: [null],
            code: [null],
            name: [null],
            url: [null],
            createTime: [null],
            deleted: [null],
            delTime: [null],
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
            parentId: null,
            code: null,
            name: null,
            url: null,
            createTime: null,
            deleted: null,
            delTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.provinceId) {
            pageQuery.addOnlyFilter('id', searchObj.id, 'eq');
            pageQuery.addOnlyFilter('guid', searchObj.guid, 'eq');
            pageQuery.addOnlyFilter('parentId', searchObj.parentId, 'eq');
            pageQuery.addOnlyFilter('code', searchObj.code, 'eq');
            pageQuery.addOnlyFilter('name', searchObj.name, 'eq');
            pageQuery.addOnlyFilter('url', searchObj.url, 'eq');
            pageQuery.addOnlyFilter('createTime', searchObj.createTime, 'eq');
            pageQuery.addOnlyFilter('deleted', searchObj.deleted, 'eq');
            pageQuery.addOnlyFilter('delTime', searchObj.delTime, 'eq');

        }
        return pageQuery;
    }


}
