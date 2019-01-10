

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {ExpressTemplate} from '../../models/original/express-template.model';
import {ExpressTemplateService} from '../../../services/express-template.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
  selector: 'app-view-express-template',
  templateUrl: './view-express-template.component.html',
  styleUrls: ['./view-express-template.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewExpressTemplateComponent implements OnInit {

    expressTemplate: ExpressTemplate=new ExpressTemplate;

    constructor(private route: ActivatedRoute,private location: Location,
                private expressTemplateService: ExpressTemplateService, private dialogService: DialogsService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.expressTemplateService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.expressTemplate = response.data;
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
