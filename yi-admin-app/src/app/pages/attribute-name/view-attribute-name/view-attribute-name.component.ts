

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {AttributeName} from '../../models/original/attribute-name.model';
import {AttributeNameService} from '../../../services/attribute-name.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-attribute-name',
  templateUrl: './view-attribute-name.component.html',
  styleUrls: ['./view-attribute-name.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAttributeNameComponent implements OnInit {

    attributeName: AttributeName=new AttributeName;

    constructor(private route: ActivatedRoute,private location: Location,
                private attributeNameService: AttributeNameService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.attributeNameService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.attributeName = response.data;
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
