


import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {WithdrawService} from '../../../services/withdraw.service';
import {Withdraw} from '../../models/original/withdraw.model';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";
import {SUCCESS} from "../../models/constants.model";

@Component({
  selector: 'app-list-withdraw',
  templateUrl: './list-withdraw.component.html',
  styleUrls: ['./list-withdraw.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListWithdrawComponent extends CommonList implements OnInit {

 constructor(public route: ActivatedRoute, public router: Router,private withdrawService: WithdrawService, public dialogService: DialogsService) {
        super(route, router,dialogService,withdrawService);
    }

        list=[{
            key:"待审核",
            value:"false"
        },{
             key:"已审核",
             value:"true"
            },{
            key:"全部",
            value:""
        }]
    toexamine(val){
       this.pageQuery.addOnlyFilter('isWithdraw',val,'eq')
    }

    ngOnInit() {
        this.getDatas();
    }

    getToexamine(id){
        this.dialogService.confirm('提示', '是否确认审核？').then(result => {
            if (result) {
                this.withdrawService.toAudited(id).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        });

    }
}
