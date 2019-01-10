

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Region} from '../../models/original/region.model';
import {RegionService} from '../../../services/region.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-region',
    templateUrl: './view-region.component.html',
    styleUrls: ['./view-region.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewRegionComponent implements OnInit {

    region: Region = new Region;

    constructor(private route: ActivatedRoute, private location: Location,
        private regionService: RegionService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.regionService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.region = response.data;
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
