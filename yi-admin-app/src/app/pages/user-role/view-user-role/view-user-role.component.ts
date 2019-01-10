

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {UserRole} from '../../models/original/user-role.model';
import {UserRoleService} from '../../../services/user-role.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-user-role',
  templateUrl: './view-user-role.component.html',
  styleUrls: ['./view-user-role.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewUserRoleComponent implements OnInit {

    userRole: UserRole=new UserRole;

    constructor(private route: ActivatedRoute,private location: Location,
                private userRoleService: UserRoleService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.userRoleService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.userRole = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    goBack(){
        this.location.back();
    }

}
