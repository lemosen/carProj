

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Dept} from '../../models/original/dept.model';
import {DeptService} from '../../../services/dept.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-dept',
  templateUrl: './view-dept.component.html',
  styleUrls: ['./view-dept.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewDeptComponent implements OnInit {

    dept: Dept=new Dept;

    constructor(private route: ActivatedRoute,private location: Location,
                private deptService: DeptService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.deptService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dept = response.data;
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
