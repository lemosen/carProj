import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Withdraw} from '../../models/original/withdraw.model';
import {WithdrawService} from '../../../services/withdraw.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-withdraw',
    templateUrl: './view-withdraw.component.html',
    styleUrls: ['./view-withdraw.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewWithdrawComponent implements OnInit {

    withdraw: Withdraw = new Withdraw;

    constructor(private route: ActivatedRoute, private location: Location,
                private withdrawService: WithdrawService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.withdrawService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.withdraw = response.data;
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
