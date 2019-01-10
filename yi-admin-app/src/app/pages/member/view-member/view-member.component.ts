

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Member} from '../../models/original/member.model';
import {MemberService} from '../../../services/member.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-member',
  templateUrl: './view-member.component.html',
  styleUrls: ['./view-member.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewMemberComponent implements OnInit {
    i : number = 0;

    member: Member=new Member;
    switch:boolean=true;
    constructor(private route: ActivatedRoute,private location: Location,
                private memberService: MemberService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.memberService.getById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.member = response.data;
                console.log( response.data);
                console.log( this.member);
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
