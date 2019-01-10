import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {BasicConfigService} from '../../../services/basic-config.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-basic-config',
    templateUrl: './list-basic-config.component.html',
    styleUrls: ['./list-basic-config.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListBasicConfigComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private basicConfigService: BasicConfigService, public dialogService: DialogsService) {
        super(route, router, dialogService, basicConfigService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
