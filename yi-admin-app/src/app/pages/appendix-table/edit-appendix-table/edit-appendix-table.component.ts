import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AppendixTableService} from '../../../services/appendix-table.service';
import {AppendixTable} from '../../models/original/appendix-table.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-appendix-table',
    templateUrl: './edit-appendix-table.component.html',
    styleUrls: ['./edit-appendix-table.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditAppendixTableComponent implements OnInit {

    submitted: boolean = false;

    appendixTable: AppendixTable;

    constructor(private route: ActivatedRoute, private router: Router, private appendixTableService: AppendixTableService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.appendixTableService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.appendixTable = response.data;
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
                this.appendixTableService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/appendixTable/list']);
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
