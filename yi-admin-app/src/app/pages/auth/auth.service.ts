import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {BaseService} from '../../services/base.service';
import {AppConfig} from '../configs/app-config';

@Injectable()
export class AuthService extends BaseService {

    constructor(protected http: HttpClient, protected appConfig: AppConfig) {
        super(http, appConfig, "auth");
    }

    /**
     * 用户登录
     * @param {string} userName
     * @param {string} password
     * @returns {Observable<any>}
     */
    public login(userName: string, password: string): Observable<any> {
        return this.postFormData("login", {userName: userName, password: password});
    }

    /**
     * 根据令牌重新获取登录数据
     * @param {string} token
     * @returns {Observable<any>}
     */
    public loginByToken(token: string): Observable<any> {
        return this.postFormData("loginByToken", {token: token});
    }
}
