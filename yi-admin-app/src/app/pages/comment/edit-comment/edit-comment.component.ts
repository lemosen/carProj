

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {CommentService} from "../../components/comment/comment.service";
import {Comment} from '../../models/original/comment.model';

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditCommentComponent implements OnInit {

  submitted: boolean = false;

  comment: Comment;

  constructor(private route: ActivatedRoute,private router:Router,private commentService: CommentService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.commentService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.comment = response.data;
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
                formValue.obj.id = this.comment.id;
                this.commentService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                                    this.router.navigate(['/pages/comment/list']);
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
