import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {AccountLevel} from '../../models/original/account-level.model';
import {AccountLevelService} from '../../../services/account-level.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-account-level',
    templateUrl: './view-account-level.component.html',
    styleUrls: ['./view-account-level.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewAccountLevelComponent implements OnInit {

    accountLevel: AccountLevel = new AccountLevel;

    constructor(private route: ActivatedRoute, private location: Location,
                private accountLevelService: AccountLevelService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.accountLevelService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.accountLevel = response.data;
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
