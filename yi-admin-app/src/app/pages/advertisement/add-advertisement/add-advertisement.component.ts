
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Advertisement} from '../../models/original/advertisement.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AdvertisementService} from '../../../services/advertisement.service';

@Component({
    selector: 'app-add-advertisement',
    templateUrl: './add-advertisement.component.html',
    styleUrls: ['./add-advertisement.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAdvertisementComponent implements OnInit {

    submitted: boolean = false;

    advertisement: Advertisement;

    constructor(private router:Router,private advertisementService: AdvertisementService, private dialogService: DialogsService) {
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
                this.advertisementService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/advertisement/list']);
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
