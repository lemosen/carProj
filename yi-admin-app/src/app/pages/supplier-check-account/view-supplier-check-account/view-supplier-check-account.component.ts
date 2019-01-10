

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {SupplierCheckAccount} from '../../models/original/supplier-check-account.model';
import {SupplierCheckAccountService} from '../../../services/supplier-check-account.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-supplier-check-account',
  templateUrl: './view-supplier-check-account.component.html',
  styleUrls: ['./view-supplier-check-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSupplierCheckAccountComponent implements OnInit {

    supplierCheckAccount: SupplierCheckAccount=new SupplierCheckAccount;

    constructor(private route: ActivatedRoute,private location: Location,
                private supplierCheckAccountService: SupplierCheckAccountService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierCheckAccountService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierCheckAccount = response.data;
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
