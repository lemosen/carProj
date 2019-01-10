import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AddressService} from '../../../services/address.service';
import {Address} from '../../models/original/address.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
    selector: 'app-edit-address',
    templateUrl: './edit-address.component.html',
    styleUrls: ['./edit-address.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditAddressComponent implements OnInit {

    submitted: boolean = false;

    address: Address;

    constructor(private route: ActivatedRoute, private router: Router, private addressService: AddressService, private dialogService: DialogsService) {
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

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }

    submitForm(formValue) {
        if (this.submitted) {
            return;
        }
        this.dialogService.confirm('提示', '确认要提交吗？').then(result => {
            if (result) {
                this.submitted = true;
                this.addressService.update(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/address/list']);
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        });
    }
}
