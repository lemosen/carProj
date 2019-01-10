import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {Address} from '../../models/original/address.model';
import {AddressService} from '../../../services/address.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-view-address',
    templateUrl: './view-address.component.html',
    styleUrls: ['./view-address.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewAddressComponent implements OnInit {

    address: Address = new Address;

    constructor(private route: ActivatedRoute, private location: Location,
                private addressService: AddressService, private dialogService: DialogsService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.addressService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.address = response.data;
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    goBack() {
        this.location.back();
    }

}
