import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {AppendixTable} from '../../models/original/appendix-table.model';
import {AppendixTableService} from '../../../services/appendix-table.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-appendix-table',
    templateUrl: './view-appendix-table.component.html',
    styleUrls: ['./view-appendix-table.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewAppendixTableComponent implements OnInit {

    appendixTable: AppendixTable = new AppendixTable;

    constructor(private route: ActivatedRoute, private location: Location,
                private appendixTableService: AppendixTableService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.appendixTableService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.appendixTable = response.data;
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
