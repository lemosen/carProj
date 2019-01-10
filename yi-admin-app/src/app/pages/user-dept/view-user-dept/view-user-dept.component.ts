import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {UserDept} from '../../models/original/user-dept.model';
import {UserDeptService} from '../../../services/user-dept.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-user-dept',
    templateUrl: './view-user-dept.component.html',
    styleUrls: ['./view-user-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewUserDeptComponent implements OnInit {

    userDept: UserDept = new UserDept;

    constructor(private route: ActivatedRoute, private location: Location,
                private userDeptService: UserDeptService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.userDeptService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.userDept = response.data;
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
