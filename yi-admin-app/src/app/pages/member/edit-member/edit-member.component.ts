

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {MemberService} from '../../../services/member.service';
import {Member} from '../../models/original/member.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-member',
  templateUrl: './edit-member.component.html',
  styleUrls: ['./edit-member.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditMemberComponent implements OnInit {

  submitted: boolean = false;

  member: Member;

  constructor(private route: ActivatedRoute,private router:Router,private memberService: MemberService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.memberService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.member = response.data;
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
                formValue.obj.id = this.member.id;
                this.memberService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/member/list']);
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
