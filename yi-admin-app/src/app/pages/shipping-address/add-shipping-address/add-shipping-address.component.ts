
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {ShippingAddress} from '../../models/original/shipping-address.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ShippingAddressService} from '../../../services/shipping-address.service';

@Component({
    selector: 'app-add-shipping-address',
    templateUrl: './add-shipping-address.component.html',
    styleUrls: ['./add-shipping-address.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddShippingAddressComponent implements OnInit {

    submitted: boolean = false;

    shippingAddress: ShippingAddress;

    constructor(private router:Router,private shippingAddressService: ShippingAddressService, private dialogService: DialogsService) {
    }

    ngOnInit() {
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
        this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                this.submitted = true;
                this.shippingAddressService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/shippingAddress/list']);
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        }, () => {
            this.submitted = false;
        });
    }
}
