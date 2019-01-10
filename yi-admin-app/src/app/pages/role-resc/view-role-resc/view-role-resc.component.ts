

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {RoleResc} from '../../models/original/role-resc.model';
import {RoleRescService} from '../../../services/role-resc.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-role-resc',
  templateUrl: './view-role-resc.component.html',
  styleUrls: ['./view-role-resc.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRoleRescComponent implements OnInit {

    roleResc: RoleResc=new RoleResc;

    constructor(private route: ActivatedRoute,private location: Location,
                private roleRescService: RoleRescService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.roleRescService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.roleResc = response.data;
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
