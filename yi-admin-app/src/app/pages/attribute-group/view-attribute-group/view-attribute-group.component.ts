

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {AttributeGroup} from '../../models/original/attribute-group.model';
import {AttributeGroupService} from '../../../services/attribute-group.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-attribute-group',
  templateUrl: './view-attribute-group.component.html',
  styleUrls: ['./view-attribute-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAttributeGroupComponent implements OnInit {

    attributeGroup: AttributeGroup=new AttributeGroup;

    constructor(private route: ActivatedRoute,private location: Location,
                private attributeGroupService: AttributeGroupService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.attributeGroupService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.attributeGroup = response.data;
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
