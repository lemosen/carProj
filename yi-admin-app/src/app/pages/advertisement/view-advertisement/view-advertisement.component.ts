

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Advertisement} from '../../models/original/advertisement.model';
import {AdvertisementService} from '../../../services/advertisement.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-advertisement',
  templateUrl: './view-advertisement.component.html',
  styleUrls: ['./view-advertisement.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAdvertisementComponent implements OnInit {

    advertisement: Advertisement=new Advertisement;

    constructor(private route: ActivatedRoute,private location: Location,
                private advertisementService: AdvertisementService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.advertisementService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.advertisement = response.data;
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
