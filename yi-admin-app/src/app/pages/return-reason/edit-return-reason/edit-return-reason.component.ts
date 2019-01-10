

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ReturnReasonService} from '../../../services/return-reason.service';
import {ReturnReason} from '../../models/original/return-reason.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-return-reason',
  templateUrl: './edit-return-reason.component.html',
  styleUrls: ['./edit-return-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditReturnReasonComponent implements OnInit {

  submitted: boolean = false;

  returnReason: ReturnReason;

  constructor(private route: ActivatedRoute,private router:Router,private returnReasonService: ReturnReasonService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.returnReasonService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.returnReason = response.data;
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
                formValue.obj.id=this.returnReason.id;
                this.returnReasonService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/return-reason/list']);
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
