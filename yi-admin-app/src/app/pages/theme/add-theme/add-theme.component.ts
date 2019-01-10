
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Article} from '../../models/original/article.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ArticleService} from '../../../services/article.service';

@Component({
    selector: 'app-add-theme',
    templateUrl: './add-theme.component.html',
    styleUrls: ['./add-theme.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddThemeComponent implements OnInit {

    submitted: boolean = false;

    article: Article;

    constructor(private router:Router,private articleService: ArticleService, private dialogService: DialogsService) {
    }

    ngOnInit() {
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
        this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                formValue.obj.articleType=2
                this.submitted = true;
                this.articleService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/theme/list']);
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        }, () => {
            this.submitted = false;
        });
    }
}
