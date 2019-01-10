import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SupplierWithdraw} from '../../../models/original/supplier-withdraw.model';
import {SupplierWithdrawService} from '../../../services/supplier-withdraw.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-supplier-withdraw',
    templateUrl: './add-supplier-record.component.html',
    styleUrls: ['./add-supplier-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddSupplierRecordComponent implements OnInit {

    submitting = false;

    supplierWithdraw: SupplierWithdraw;

    constructor(private router:Router,private supplierWithdrawService: SupplierWithdrawService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
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
            this.supplierWithdrawService.add(formValue.obj).subscribe(response => {
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
