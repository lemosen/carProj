import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {ProvinceCity} from '../../models/original/province-city.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {ProvinceCityService} from '../../../services/province-city.service';

@Component({
    selector: 'app-add-province-city',
    templateUrl: './add-province-city.component.html',
    styleUrls: ['./add-province-city.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddProvinceCityComponent implements OnInit {

    submitted: boolean = false;

    provinceCity: ProvinceCity;

    constructor(private router: Router, private provinceCityService: ProvinceCityService, private dialogService: DialogsService) {
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
                this.provinceCityService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/provinceCity/list']);
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
