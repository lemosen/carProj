

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AttributeNameService} from '../../../services/attribute-name.service';
import {AttributeName} from '../../models/original/attribute-name.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-attribute-name',
  templateUrl: './edit-attribute-name.component.html',
  styleUrls: ['./edit-attribute-name.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditAttributeNameComponent implements OnInit {

  submitted: boolean = false;

  attributeName: AttributeName;

  constructor(private route: ActivatedRoute,private router:Router,private attributeNameService: AttributeNameService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.attributeNameService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.attributeName = response.data;
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
                formValue.obj.id=this.attributeName.id;
                this.submitted = true;
                this.attributeNameService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/attribute-name/list']);
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
