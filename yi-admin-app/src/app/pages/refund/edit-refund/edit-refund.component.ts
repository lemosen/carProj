import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {RefundService} from '../../../services/refund.service';
import {Refund} from '../../models/original/refund.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-refund',
    templateUrl: './edit-refund.component.html',
    styleUrls: ['./edit-refund.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditRefundComponent implements OnInit {

    submitted: boolean = false;

    refund: Refund;

    constructor(private route: ActivatedRoute, private router: Router, private refundService: RefundService, private dialogService: DialogsService) {
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
                this.refundService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/refund/list']);
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
