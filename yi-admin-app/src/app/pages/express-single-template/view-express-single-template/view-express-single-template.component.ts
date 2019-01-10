import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Refund} from '../../models/original/refund.model';
import {RefundService} from '../../../services/refund.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-express-single-template',
    templateUrl: './view-express-single-template.component.html',
    styleUrls: ['./view-express-single-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewExpressSingleTemplateComponent implements OnInit {

    refund: Refund = new Refund;

    constructor(private route: ActivatedRoute, private location: Location,
                private refundService: RefundService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.refundService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.refund = response.data;
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
