

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {MemberLevel} from '../../models/original/member-level.model';
import {MemberLevelService} from '../../../services/member-level.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-member-level',
  templateUrl: './view-member-level.component.html',
  styleUrls: ['./view-member-level.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewMemberLevelComponent implements OnInit {

    memberLevel: MemberLevel=new MemberLevel;

    constructor(private route: ActivatedRoute,private location: Location,
                private memberLevelService: MemberLevelService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.memberLevelService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.memberLevel = response.data;
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
