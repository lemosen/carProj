

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Banner} from '../../models/original/banner.model';
import {BannerService} from '../../../services/banner.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-banner',
  templateUrl: './view-banner.component.html',
  styleUrls: ['./view-banner.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewBannerComponent implements OnInit {

    banner: Banner=new Banner;

    constructor(private route: ActivatedRoute,private location: Location,
                private bannerService: BannerService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.bannerService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.banner = response.data;
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
