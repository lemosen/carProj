

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Role} from '../../models/original/role.model';
import {RoleService} from '../../../services/role.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-role',
  templateUrl: './view-role.component.html',
  styleUrls: ['./view-role.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRoleComponent implements OnInit {

    role: Role=new Role;

    constructor(private route: ActivatedRoute,private location: Location,
                private roleService: RoleService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.roleService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.role = response.data;
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
