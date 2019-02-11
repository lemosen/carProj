import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {SupplierWithdrawService} from '../../../services/supplier-withdraw.service';
import {SupplierWithdraw} from '../../../models/original/supplier-withdraw.model';

@Component({
  selector: 'edit-supplier-withdraw',
  templateUrl: './edit-supplier-withdraw.component.html',
  styleUrls: ['./edit-supplier-withdraw.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditSupplierWithdrawComponent implements OnInit {

    submitting = false;

supplierWithdraw: SupplierWithdraw;

    constructor(private route: ActivatedRoute,private router:Router,private supplierWithdrawService: SupplierWithdrawService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierWithdrawService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierWithdraw = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.supplierWithdraw.id;
            this.supplierWithdrawService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/supplier-withdraw/list']);
                } else {
                    this.msgSrv.error('请求失败'+response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误'+error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}
