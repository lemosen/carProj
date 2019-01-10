

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AccountRecordService} from '../../../services/account-record.service';
import {AccountRecord} from '../../models/original/account-record.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-account-record',
  templateUrl: './edit-account-record.component.html',
  styleUrls: ['./edit-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditAccountRecordComponent implements OnInit {

  submitted: boolean = false;

  accountRecord: AccountRecord;

  constructor(private route: ActivatedRoute,private router:Router,private accountRecordService: AccountRecordService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.accountRecordService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.accountRecord = response.data;
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
                this.accountRecordService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/accountRecord/list']);
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
