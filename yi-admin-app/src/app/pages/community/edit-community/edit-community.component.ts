

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {CommunityService} from '../../../services/community.service';
import {Community} from '../../models/original/community.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {MemberService} from "../../../services/member.service";
import {PageQuery} from "../../models/page-query.model";

@Component({
  selector: 'app-edit-community',
  templateUrl: './edit-community.component.html',
  styleUrls: ['./edit-community.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditCommunityComponent implements OnInit {

  submitted: boolean = false;

  community: Community;

  constructor(private route: ActivatedRoute,private router:Router,private communityService: CommunityService,public memberService:MemberService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.communityService.getById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.community = response.data;
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
                 formValue.obj.id=this.community.id
                this.communityService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/community/list']);
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
