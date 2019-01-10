

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ExpressTemplateService} from '../../../services/express-template.service';
import {ExpressTemplate} from '../../models/original/express-template.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-express-template',
  templateUrl: './edit-express-template.component.html',
  styleUrls: ['./edit-express-template.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditExpressTemplateComponent implements OnInit {

  submitted: boolean = false;

  expressTemplate: ExpressTemplate;

  constructor(private route: ActivatedRoute,private router:Router,private expressTemplateService: ExpressTemplateService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.expressTemplateService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.expressTemplate = response.data;
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
                formValue.obj.id = this.expressTemplate.id;
                this.expressTemplateService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/express-template/list']);
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
