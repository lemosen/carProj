

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Resc} from '../../models/original/resc.model';
import {RescService} from '../../../services/resc.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-resc',
  templateUrl: './view-resc.component.html',
  styleUrls: ['./view-resc.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRescComponent implements OnInit {

    resc: Resc=new Resc;

    constructor(private route: ActivatedRoute,private location: Location,
                private rescService: RescService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.rescService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.resc = response.data;
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
