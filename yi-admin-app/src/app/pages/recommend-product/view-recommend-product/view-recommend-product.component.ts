

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {RecommendProduct} from '../../models/original/recommend-product.model';
import {RecommendProductService} from '../../../services/recommend-product.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-recommend-product',
  templateUrl: './view-recommend-product.component.html',
  styleUrls: ['./view-recommend-product.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRecommendProductComponent implements OnInit {

    recommendProduct: RecommendProduct=new RecommendProduct;

    constructor(private route: ActivatedRoute,private location: Location,
                private recommendProductService: RecommendProductService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.recommendProductService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.recommendProduct = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    goBack(){
        this.location.back();
    }

}
