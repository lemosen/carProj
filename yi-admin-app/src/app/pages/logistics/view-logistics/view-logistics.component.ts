import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Logistics} from '../../models/original/logistics.model';
import {LogisticsService} from '../../../services/logistics.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-logistics',
    templateUrl: './view-logistics.component.html',
    styleUrls: ['./view-logistics.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewLogisticsComponent implements OnInit {

    logistics: Logistics = new Logistics;

    constructor(private route: ActivatedRoute, private location: Location,
                private logisticsService: LogisticsService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.logisticsService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.logistics = response.data;
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
