import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ProvinceCity} from '../../models/original/province-city.model';
import {ProvinceCityService} from '../../../services/province-city.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-province-city',
    templateUrl: './view-province-city.component.html',
    styleUrls: ['./view-province-city.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewProvinceCityComponent implements OnInit {

    provinceCity: ProvinceCity = new ProvinceCity;

    constructor(private route: ActivatedRoute, private location: Location,
                private provinceCityService: ProvinceCityService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.provinceCityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.provinceCity = response.data;
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
