import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Evaluation} from '../../models/original/evaluation.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {EvaluationService} from '../../../services/evaluation.service';

@Component({
    selector: 'app-add-evaluation',
    templateUrl: './add-evaluation.component.html',
    styleUrls: ['./add-evaluation.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddEvaluationComponent implements OnInit {

    submitted: boolean = false;

    evaluation: Evaluation;

    constructor(private router: Router, private evaluationService: EvaluationService, private dialogService: DialogsService) {
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
                this.submitted = true;
                this.evaluationService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/evaluation/list']);
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
