import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {AppendixTable} from '../../models/original/appendix-table.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AppendixTableService} from '../../../services/appendix-table.service';

@Component({
    selector: 'app-add-appendix-table',
    templateUrl: './add-appendix-table.component.html',
    styleUrls: ['./add-appendix-table.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAppendixTableComponent implements OnInit {

    submitted: boolean = false;

    appendixTable: AppendixTable;

    constructor(private router: Router, private appendixTableService: AppendixTableService, private dialogService: DialogsService) {
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
                this.appendixTableService.add(formValue.obj).subscribe(response => {
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
        }, () => {
            this.submitted = false;
        });
    }
}
