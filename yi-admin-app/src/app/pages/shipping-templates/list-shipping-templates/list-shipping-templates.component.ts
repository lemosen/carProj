import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {RefundService} from '../../../services/refund.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-shipping-templates',
    templateUrl: './list-shipping-templates.component.html',
    styleUrls: ['./list-shipping-templates.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListShippingTemplatesComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private refundService: RefundService, public dialogService: DialogsService) {
        super(route, router, dialogService, refundService);
    }




    ngOnInit() {
        this.getDatas();
    }
}
