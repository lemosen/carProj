import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {RefundOrder} from '../../models/original/refund-order.model';
import {RefundOrderService} from '../../../services/refund-order.service';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {SUCCESS} from '../../models/constants.model';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ObjectUtils} from "../../../shared/utils/ObjectUtils";
import {PageQuery} from "../../models/page-query.model";
import {UserService} from "../../../services/user.service";
import {MemberLevelService} from "../../../services/member-level.service";


@Component({
    selector: 'app-view-refund-order',
    templateUrl: './view-refund-order.component.html',
    styleUrls: ['./view-refund-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewRefundOrderComponent implements OnInit {

    refundOrder: RefundOrder = new RefundOrder;

    commonForm: FormGroup;

    submitted: boolean = false;

    constructor(private fb: FormBuilder, private route: ActivatedRoute, private router: Router, private location: Location, public memberLevelService: MemberLevelService,
                private refundOrderService: RefundOrderService, public userService: UserService, private dialogService: DialogsService) {
        this.buildForm();
    }


    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.refundOrderService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.refundOrder = response.data;
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

    setMemberLevel(event) {
        this.commonForm.patchValue({
            refundProcesse: {
                user: {
                    id: event.id,
                    fullName: event.fullName
                }
            }
        });
    }

    formErrors = {
        id: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        refundNo: []
        ,
        refundProcesse: {
            remark: {
                name: '',
                msg: '',
            },

        },
        user: {
            id: {
                name: '',
                msg: '',
            },
            fullName: {
                name: '',
                msg: '',
            },
        },
    };

    buildForm(): void {
        this.commonForm = this.fb.group({
            id: [],
            refundProcesse: this.fb.group({

                user: {
                    id: [null],
                    fullName: "请选择"

                    ,
                },
                remark: [null],
            }),
            refundNo: []

        });
    }

    setBuildFormValue(refundOrder: RefundOrder) {
        this.commonForm.setValue({
            id:
            refundOrder.id
            ,

            refundProcesse: {

                remark:
                refundOrder.refundProcesse.remark
                ,
                user: {
                    id: refundOrder.user.id,
                    fullName: refundOrder.user.fullName
                }


            },
        });
    }

    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
        if (commonFormValid) {
            return this.commonForm.value;
        }
        return null;
    }

    reset() {

    }

    goRefundOrder(id, state) {
        const formValue = this.submitCheck();
        if (this.refundOrder) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if (this.commonForm.value.refundProcesse.user.fullName == "请选择") {
            this.dialogService.toast('warning', '提示', '请选择处理人员！');
            return;
        }
        /**
         * 将 refundOrder的id 赋给 process 的退款单id
         */

        this.commonForm.value.refundProcesse.refundOrderId = id;
        const searchObj = this.commonForm.value;
        if (this.submitted) {
            return;
        }
        this.dialogService.confirm('提示', '确认要提交吗？').then(result => {
            if (result) {
                searchObj.id = this.refundOrder.id;
                searchObj.refundNo = this.refundOrder.refundNo
                searchObj.state = state
                this.submitted = true;
                this.refundOrderService.toExamine(searchObj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/refund-order/list']);
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
