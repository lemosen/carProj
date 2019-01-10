import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Evaluation} from '../../models/original/evaluation.model';
import {EvaluationService} from '../../../services/evaluation.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-evaluation',
    templateUrl: './view-evaluation.component.html',
    styleUrls: ['./view-evaluation.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewEvaluationComponent implements OnInit {

    evaluation: Evaluation = new Evaluation;

    constructor(private route: ActivatedRoute, private location: Location,
                private evaluationService: EvaluationService, private dialogService: DialogsService) {
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

    goBack() {
        this.location.back();
    }

}
