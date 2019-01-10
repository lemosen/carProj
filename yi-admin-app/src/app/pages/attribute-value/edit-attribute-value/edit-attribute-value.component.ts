

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AttributeValueService} from '../../../services/attribute-value.service';
import {AttributeValue} from '../../models/original/attribute-value.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-attribute-value',
  templateUrl: './edit-attribute-value.component.html',
  styleUrls: ['./edit-attribute-value.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditAttributeValueComponent implements OnInit {

  submitted: boolean = false;

  attributeValue: AttributeValue;

  constructor(private route: ActivatedRoute,private router:Router,private attributeValueService: AttributeValueService, private dialogService: DialogsService) { }

  ngOnInit() {

      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);

      });
  }

  getById(objId){
      this.attributeValueService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.attributeValue = response.data;
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
                formValue.obj.id = this.attributeValue.id;
                this.submitted = true;
                this.attributeValueService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/attribute-value/list']);
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
