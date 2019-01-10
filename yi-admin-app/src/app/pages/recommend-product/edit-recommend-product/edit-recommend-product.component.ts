

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {SUCCESS} from '../../models/constants.model';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {RecommendProductService} from '../../../services/recommend-product.service';
import {RecommendProduct} from '../../models/original/recommend-product.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-edit-recommend-product',
  templateUrl: './edit-recommend-product.component.html',
  styleUrls: ['./edit-recommend-product.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditRecommendProductComponent implements OnInit {

  submitted: boolean = false;

  recommendProduct: RecommendProduct;

  constructor(private route: ActivatedRoute,private router:Router,private recommendProductService: RecommendProductService, private dialogService: DialogsService) { }

  ngOnInit() {
      this.route.params.subscribe((params: ParamMap) => {
          this.getById(params["objId"]);
      });
  }

  getById(objId){
      this.recommendProductService.getVoById(objId).subscribe(response => {
          if (response.result == SUCCESS) {
              this.recommendProduct = response.data;
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
                this.recommendProductService.update(formValue.obj).subscribe(response => {
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
        });
    }
}
