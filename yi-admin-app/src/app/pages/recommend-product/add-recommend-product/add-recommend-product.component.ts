
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {RecommendProduct} from '../../models/original/recommend-product.model';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {RecommendProductService} from '../../../services/recommend-product.service';

@Component({
    selector: 'app-add-recommend-product',
    templateUrl: './add-recommend-product.component.html',
    styleUrls: ['./add-recommend-product.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddRecommendProductComponent implements OnInit {

    submitted: boolean = false;

    recommendProduct: RecommendProduct;

    constructor(private router:Router,private recommendProductService: RecommendProductService, private dialogService: DialogsService) {
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
                this.recommendProductService.add(formValue.obj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/recommendProduct/list']);
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
