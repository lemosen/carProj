

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {CommodityService} from '../../../services/commodity.service';
import {Commodity} from '../../models/original/commodity.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {RegionService} from "../../../services/region.service";
import {Region} from "../../models/original/region.model";

@Component({
  selector: 'app-edit-commodity',
  templateUrl: './edit-commodity.component.html',
  styleUrls: ['./edit-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditCommodityComponent implements OnInit {

  submitted: boolean = false;

  commodity: Commodity;

  constructor(private route: ActivatedRoute,private router:Router,private commodityService: CommodityService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });

  }

  getById(objId){
      this.commodityService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.commodity = response.data;
              console.log( this.commodity)
          } else {
              this.dialogService.alert('请求失败', response.message);
          }
      }, error => {
          this.dialogService.alert('请求错误', error.message);
      });
  }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }

    submitForm(formValue) {
        if (this.submitted) {
            return;
        }
        this.dialogService.confirm('提示', '确认要提交吗？').then(result => {
            if (result) {
                formValue.obj.id = this.commodity.id;
                this.submitted = true;
                this.commodityService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/commodity/list']);
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        });
    }
}
