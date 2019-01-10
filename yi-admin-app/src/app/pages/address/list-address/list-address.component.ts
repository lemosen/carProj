import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AddressService} from '../../../services/address.service';
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";

@Component({
    selector: 'app-list-address',
    templateUrl: './list-address.component.html',
    styleUrls: ['./list-address.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListAddressComponent extends CommonList implements OnInit {

    constructor(public route: ActivatedRoute, public router: Router, private addressService: AddressService, public dialogService: DialogsService) {
        super(route, router, dialogService, addressService);
    }

    ngOnInit() {
        this.getDatas();
    }
}
