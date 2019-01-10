

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AttributeGroupService} from '../../../services/attribute-group.service';
import {AttributeGroup} from '../../models/original/attribute-group.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-attribute-group',
  templateUrl: './edit-attribute-group.component.html',
  styleUrls: ['./edit-attribute-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditAttributeGroupComponent implements OnInit {

  submitted: boolean = false;

  attributeGroup: AttributeGroup;

  constructor(private route: ActivatedRoute,private router:Router,private attributeGroupService: AttributeGroupService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.attributeGroupService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.attributeGroup = response.data;
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
                formValue.obj.id=this.attributeGroup.id;
                this.attributeGroupService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/attribute-group/list']);
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
