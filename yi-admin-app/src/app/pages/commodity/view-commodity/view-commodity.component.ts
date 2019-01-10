import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Commodity} from '../../models/original/commodity.model';
import {CommodityService} from '../../../services/commodity.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-commodity',
    templateUrl: './view-commodity.component.html',
    styleUrls: ['./view-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewCommodityComponent implements OnInit {

    commodity: Commodity = new Commodity;

    constructor(private route: ActivatedRoute, private location: Location,
                private commodityService: CommodityService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.commodityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.commodity = response.data;
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
