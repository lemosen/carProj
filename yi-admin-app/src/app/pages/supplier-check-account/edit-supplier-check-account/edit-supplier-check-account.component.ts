

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SupplierCheckAccountService} from '../../../services/supplier-check-account.service';
import {SupplierCheckAccount} from '../../models/original/supplier-check-account.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-supplier-check-account',
  templateUrl: './edit-supplier-check-account.component.html',
  styleUrls: ['./edit-supplier-check-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditSupplierCheckAccountComponent implements OnInit {

  submitted: boolean = false;

  supplierCheckAccount: SupplierCheckAccount;

  constructor(private route: ActivatedRoute,private router:Router,private supplierCheckAccountService: SupplierCheckAccountService, private dialogService: DialogsService) { }

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
                this.submitted = true;
                formValue.obj.id = this.supplierCheckAccount.id;
                this.supplierCheckAccountService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/supplierCheckAccount/list']);
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
