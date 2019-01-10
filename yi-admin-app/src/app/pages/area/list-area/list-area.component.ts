import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AreaService} from '../../../services/area.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';

@Component({
    selector: 'app-list-area',
    templateUrl: './list-area.component.html',
    styleUrls: ['./list-area.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAreaComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    provinces: Array<{ text: string, value: string }> = [
        {text: "广东省", value: 'GD'},
        {text: "湖南省", value: 'HN'},
    ];

    constructor(public route: ActivatedRoute, public router: Router, private areaService: AreaService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, areaService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            provinceId: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            provinceId: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.provinceId) {
            pageQuery.addOnlyFilter('provinceId', searchObj.provinceId, 'eq');
        }
        return pageQuery;
    }


}
