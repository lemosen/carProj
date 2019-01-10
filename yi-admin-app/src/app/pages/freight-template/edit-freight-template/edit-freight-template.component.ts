

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FreightTemplateService} from '../../../services/freight-template.service';
import {FreightTemplate} from '../../models/original/freight-template.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-freight-template',
  templateUrl: './edit-freight-template.component.html',
  styleUrls: ['./edit-freight-template.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditFreightTemplateComponent implements OnInit {

  submitted: boolean = false;

  freightTemplate: FreightTemplate;

  constructor(private route: ActivatedRoute,private router:Router,private freightTemplateService: FreightTemplateService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.freightTemplateService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.freightTemplate = response.data;
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
                formValue.obj.id = this.freightTemplate.id;
                this.freightTemplateService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/freight-template/list']);
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
