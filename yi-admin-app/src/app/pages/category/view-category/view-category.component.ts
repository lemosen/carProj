import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Category} from '../../models/original/category.model';
import {CategoryService} from '../../../services/category.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-category',
    templateUrl: './view-category.component.html',
    styleUrls: ['./view-category.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewCategoryComponent implements OnInit {

    category: Category = new Category;

    constructor(private route: ActivatedRoute, private location: Location,
                private categoryService: CategoryService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.categoryService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.category = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    goBack() {
        this.location.back();
    }

}
