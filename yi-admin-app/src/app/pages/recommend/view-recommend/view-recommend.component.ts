

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Recommend} from '../../models/original/recommend.model';
import {RecommendService} from '../../../services/recommend.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-recommend',
  templateUrl: './view-recommend.component.html',
  styleUrls: ['./view-recommend.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRecommendComponent implements OnInit {

    recommend: Recommend=new Recommend;

    constructor(private route: ActivatedRoute,private location: Location,
                private recommendService: RecommendService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.recommendService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.recommend = response.data;
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
