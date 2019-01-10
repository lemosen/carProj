import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ArticleCommdityService} from '../../../services/article-commdity.service';
import {ArticleCommdity} from '../../models/original/article-commdity.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-article-commdity',
    templateUrl: './edit-article-commdity.component.html',
    styleUrls: ['./edit-article-commdity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditArticleCommdityComponent implements OnInit {

    submitted: boolean = false;

    articleCommdity: ArticleCommdity;

    constructor(private route: ActivatedRoute, private router: Router, private articleCommdityService: ArticleCommdityService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.articleCommdityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.articleCommdity = response.data;
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
                this.articleCommdityService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/articleCommdity/list']);
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
