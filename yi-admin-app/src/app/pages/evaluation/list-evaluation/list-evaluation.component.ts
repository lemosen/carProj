import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {EvaluationService} from '../../../services/evaluation.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-evaluation',
    templateUrl: './list-evaluation.component.html',
    styleUrls: ['./list-evaluation.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListEvaluationComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private evaluationService: EvaluationService, public dialogService: DialogsService) {
        super(route, router, dialogService, evaluationService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
