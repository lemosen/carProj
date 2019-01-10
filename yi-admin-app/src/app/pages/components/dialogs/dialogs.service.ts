import {Injectable} from '@angular/core';
import {UserSingleDialogComponent} from "./user-dialog/user-single-dialog/user-single-dialog.component";
import {UserMultipleDialogComponent} from "./user-dialog/user-multiple-dialog/user-multiple-dialog.component";
import {DeptMultipleDialogComponent} from "./dept-dialog/dept-multiple-dialog/dept-multiple-dialog.component";
import {DeptSingleDialogComponent} from "./dept-dialog/dept-single-dialog/dept-single-dialog.component";
import {AlertDialogComponent} from "./alert-dialog/alert-dialog.component";
import {ConfirmDialogComponent} from "./confirm-dialog/confirm-dialog.component";
import {PromptDialogComponent} from "./prompt-dialog/prompt-dialog.component";
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ToastrService} from 'ngx-toastr';
import {StateDialogComponent} from './state-dialog/state-dialog.component';

@Injectable()
export class DialogsService {

    /*
     * Bootstrap Modal默认参数配置
     */
    public readonly config = {
        centered: true,
    };

    constructor(private toastService: ToastrService, private modalService: NgbModal) {
    }

    toast(type?: string, title?: string, content?: string) {
        type = type || 'success';
        switch (type) {
            case 'success' : {
                this.toastService.success(content || '操作成功！', title || '成功');
                break;
            }
            case 'info' : {
                this.toastService.info(content || '操作提示！', title || '提示');
                break;
            }
            case 'error' : {
                this.toastService.error(content || '操作错误！', title || '错误');
                break;
            }
            case 'warning' : {
                this.toastService.warning(content || '操作警告！', title || '警告');
                break;
            }
            default : {
                this.toastService.success(content || '操作成功！', title || '成功');
                break;
            }
        }
    }

    prompt(title?: string, text?: string, placeholder?: string, confirmText?: string, cancelText?: string): Promise<any> {
        const dialog: NgbModalRef = this.modalService.open(PromptDialogComponent, Object.assign({}, this.config, {size: 'sm'}));
        const instance = dialog.componentInstance;
        instance.configData.title = title || '请输入';
        instance.configData.confirmText = confirmText || '确定';
        instance.configData.cancelText = cancelText || '取消';
        instance.configData.text = text || '业务要素';
        instance.configData.placeholder = placeholder || '请输入';
        return dialog.result;
    }

    confirm(title?: string, content?: string, confirmText?: string, cancelText?: string): Promise<any> {
        const dialog: NgbModalRef = this.modalService.open(ConfirmDialogComponent, Object.assign({}, this.config, {size: 'sm'}));
        const instance = dialog.componentInstance;
        instance.configData.title = title || '提示';
        instance.configData.confirmText = confirmText || '确定';
        instance.configData.cancelText = cancelText || '取消';
        instance.configData.content = content || '';
        return dialog.result;
    }

    alert(title?: string, content?: string, confirmText?: string): Promise<any> {
        const dialog: NgbModalRef = this.modalService.open(AlertDialogComponent, Object.assign({}, this.config, {size: 'sm'}));
        const instance = dialog.componentInstance;
        instance.configData.title = title || '提示';
        instance.configData.confirmText = confirmText || '确定';
        instance.configData.content = content || '';
        return dialog.result;
    }

    //员工单选
    userSingleChoose(conditionFilters?: any, title?: string, confirmText?: string): Promise<any> {
        const dialog: NgbModalRef = this.modalService.open(UserSingleDialogComponent, this.config);
        const instance = dialog.componentInstance;
        instance.configData.title = title || '员工单选';
        instance.configData.confirmText = confirmText || '选择';
        instance.conditionFilters = conditionFilters;
        return dialog.result;
    }

    //员工多选
    userMultipleChoose(conditionFilters?: any, title?: string, confirmText?: string): Promise<any> {
        const dialog: NgbModalRef = this.modalService.open(UserMultipleDialogComponent, Object.assign({}, this.config, {size: 'lg'}));
        const instance = dialog.componentInstance;
        instance.configData.title = title || '员工多选';
        instance.configData.confirmText = confirmText || '选择';
        instance.conditionFilters = conditionFilters;
        return dialog.result;
    }

    //部门单选
    deptSingleChoose(conditionFilters?: any, title?: string, confirmText?: string): Promise<any> {
        const dialog: NgbModalRef = this.modalService.open(DeptSingleDialogComponent, this.config);
        const instance = dialog.componentInstance;
        instance.configData.title = title || '部门单选';
        instance.configData.confirmText = confirmText || '选择';
        instance.conditionFilters = conditionFilters;
        return dialog.result;
    }

    //部门多选
    deptMultipleChoose(conditionFilters?: any, title?: string, confirmText?: string): Promise<any> {
        const dialog: NgbModalRef = this.modalService.open(DeptMultipleDialogComponent, Object.assign({}, this.config, {size: 'lg'}));
        const instance = dialog.componentInstance;
        instance.configData.title = title || '部门多选';
        instance.configData.confirmText = confirmText || '选择';
        instance.conditionFilters = conditionFilters;
        return dialog.result;
    }

    //状态选择
    stateChoose(states: any, title?: string, confirmText?: string, cancelText?: string): Promise<any> {
        const dialog: NgbModalRef = this.modalService.open(StateDialogComponent, Object.assign({}, this.config, {size: 'sm'}));
        const instance = dialog.componentInstance;
        instance.configData.title = title || '状态选择';
        instance.configData.confirmText = confirmText || '确定';
        instance.configData.cancelText = cancelText || '取消';
        instance.states = states || null;
        return dialog.result;
    }
}
