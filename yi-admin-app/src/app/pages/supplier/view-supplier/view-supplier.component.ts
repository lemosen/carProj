

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Supplier} from '../../models/original/supplier.model';
import {SupplierService} from '../../../services/supplier.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-supplier',
  templateUrl: './view-supplier.component.html',
  styleUrls: ['./view-supplier.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSupplierComponent implements OnInit {

    supplier: Supplier=new Supplier;

    constructor(private route: ActivatedRoute,private location: Location,
                private supplierService: SupplierService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplier = response.data;
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
