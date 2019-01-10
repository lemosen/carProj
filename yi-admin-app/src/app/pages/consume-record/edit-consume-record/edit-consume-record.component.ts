

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ConsumeRecordService} from '../../../services/consume-record.service';
import {ConsumeRecord} from '../../models/original/consume-record.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-consume-record',
  templateUrl: './edit-consume-record.component.html',
  styleUrls: ['./edit-consume-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditConsumeRecordComponent implements OnInit {

  submitted: boolean = false;

  consumeRecord: ConsumeRecord;

  constructor(private route: ActivatedRoute,private router:Router,private consumeRecordService: ConsumeRecordService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.consumeRecordService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.consumeRecord = response.data;
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
                formValue.obj.id = this.consumeRecord.id;
                this.consumeRecordService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/consume-record/list']);
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
