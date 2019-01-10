

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {RegionService} from '../../../services/region.service';
import {Region} from '../../models/original/region.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-region',
    templateUrl: './edit-region.component.html',
    styleUrls: ['./edit-region.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditRegionComponent implements OnInit {

    submitted: boolean = false;

    region: Region;

    constructor(private route: ActivatedRoute, private router: Router, private regionService: RegionService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.regionService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.region = response.data;
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
                formValue.obj.id = this.region.id
                this.regionService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/region/list']);
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
