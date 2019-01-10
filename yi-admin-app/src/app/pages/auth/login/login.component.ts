import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {CustomValidators} from 'ng2-validation';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AppStorage} from '../../configs/app-storage';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {SUCCESS} from '../../models/constants.model';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    submitted: boolean = false;
    valForm: FormGroup;

    formErrors = {
        userName: [{
            name: 'required',
            msg: '请输入用户名称',
        }],
        password: [{
            name: 'required',
            msg: '请输入密码'
        }],
    };

    constructor(private route: ActivatedRoute, private router: Router,
                private dialogService: DialogsService,
                private authService: AuthService, private appStorage: AppStorage, public fb: FormBuilder) {

        this.valForm = fb.group({
            userName: [null, Validators.compose([Validators.required, CustomValidators.email])],
            password: [null, Validators.compose([Validators.required, Validators.minLength(6)])]
        });

    }

    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.valForm);
        if (commonFormValid) {
            return this.valForm.value;
        }
        return null;
    }

    onSubmit(event) {
        event.preventDefault();
        if (this.submitted) return;

        const formValue = this.submitCheck();
        if (formValue == null) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }

        // console.log("submit login ", formValue);
        this.submitted = true;
        this.authService.login(formValue.userName, formValue.password)
            .subscribe((loginData) => {
                    if (loginData && loginData.result === SUCCESS) {
                        // console.log("login success ", loginData.sessionData);

                        this.appStorage.loginData = loginData;
                        this.router.navigate(['pages/dashboard']);

                    } else {
                        // console.log("login error ", loginData.message);
                        this.dialogService.alert('用户登录错误', '用户名或者密码错误');
                    }
                    this.submitted = false;

                }, (error) => {
                    // console.log("login error");
                    this.dialogService.alert('用户登录错误', error.message);
                    this.submitted = false;
                }
            );
    }

    ngOnInit() {
    }

    ngAfterViewInit() {
        document.getElementById('preloader').classList.add('hide');
    }

}
