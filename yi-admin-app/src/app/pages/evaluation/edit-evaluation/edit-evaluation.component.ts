import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {EvaluationService} from '../../../services/evaluation.service';
import {Evaluation} from '../../models/original/evaluation.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-evaluation',
    templateUrl: './edit-evaluation.component.html',
    styleUrls: ['./edit-evaluation.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditEvaluationComponent implements OnInit {

    submitted: boolean = false;

    evaluation: Evaluation;

    constructor(private route: ActivatedRoute, private router: Router, private evaluationService: EvaluationService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.evaluationService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.evaluation = response.data;
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
                this.evaluationService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
