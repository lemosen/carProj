import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {DeptUser} from '../../models/original/dept-user.model';
import {DeptUserService} from '../../../services/dept-user.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-dept-user',
    templateUrl: './view-dept-user.component.html',
    styleUrls: ['./view-dept-user.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewDeptUserComponent implements OnInit {

    deptUser: DeptUser = new DeptUser;

    constructor(private route: ActivatedRoute, private location: Location,
                private deptUserService: DeptUserService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.deptUserService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.deptUser = response.data;
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
