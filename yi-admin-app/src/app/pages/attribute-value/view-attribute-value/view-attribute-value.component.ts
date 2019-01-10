

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {AttributeValue} from '../../models/original/attribute-value.model';
import {AttributeValueService} from '../../../services/attribute-value.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-attribute-value',
  templateUrl: './view-attribute-value.component.html',
  styleUrls: ['./view-attribute-value.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAttributeValueComponent implements OnInit {

    attributeValue: AttributeValue=new AttributeValue;

    constructor(private route: ActivatedRoute,private location: Location,
                private attributeValueService: AttributeValueService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.attributeValueService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.attributeValue = response.data;
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
