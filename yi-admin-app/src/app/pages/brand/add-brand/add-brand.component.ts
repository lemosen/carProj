
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {Brand} from '../../models/original/brand.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {BrandService} from '../../../services/brand.service';

@Component({
    selector: 'app-add-brand',
    templateUrl: './add-brand.component.html',
    styleUrls: ['./add-brand.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddBrandComponent implements OnInit {

    submitted: boolean = false;

    brand: Brand;

    constructor(private router:Router,private brandService: BrandService, private dialogService: DialogsService) {
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
                this.brandService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/brand/list']);
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
