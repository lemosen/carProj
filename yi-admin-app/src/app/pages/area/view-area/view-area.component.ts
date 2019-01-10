import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Area} from '../../models/original/area.model';
import {AreaService} from '../../../services/area.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-area',
    templateUrl: './view-area.component.html',
    styleUrls: ['./view-area.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewAreaComponent implements OnInit {

    area: Area = new Area;

    constructor(private route: ActivatedRoute, private location: Location,
                private areaService: AreaService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.areaService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.area = response.data;
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
