import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {BaseService} from "../../services/base.service";
import {LoginData} from "../models/auth/login-data.model";
import {AppConfig} from "./app-config";
import {HttpGlobalHeader} from "./http-global-header";
import {LocalStorageService} from "angular-2-local-storage";
import {HttpClient} from "@angular/common/http";
import {SettingsService} from '../../services/settings.service';
import {AuthService} from '../auth/auth.service';
import {UserSimple} from '../models/domain/simple/user-simple.model';

@Injectable()
export class AppStorage extends BaseService {
    public onLoginDataChange: BehaviorSubject<LoginData> = new BehaviorSubject(null);

    constructor(protected http: HttpClient, private localStorageService: LocalStorageService,
                protected appConfig: AppConfig, private settings: SettingsService,
                private authService: AuthService) {
        super(http, appConfig, "auth");

        this.settings.onLogout.subscribe((data) => {
            // 第一次会收到 data == null
            if (data) {
                this.doLogout();
            }
        });
    }

    private _loginData: LoginData;  // 用户登录后数据

    get loginData(): LoginData {
        return this._loginData;
    }

    set loginData(loginData: LoginData) {
        this._loginData = loginData;

        let token = loginData ? loginData.token : null;

        if (token) {
            // console.log("save loginData ", loginData.sessionData);
            HttpGlobalHeader.setToken(token);
            this.localStorageService.set("TOKEN", token);

            this.settings.setUserSetting("name", this.loginData.user.username);
            this.settings.setUserSetting("avatarImg", this.loginData.user.avatarImg);

            this.onLoginDataChange.next(loginData);

        } else {
            // console.log("save loginData null");
            this.settings.setUserSetting("name", "登录");

            this.clearToken();
        }
    }

    private _richEditorOptions: {  // 富文本编辑器默认参数, 参加文档 https://summernote.org/deep-dive/
        lang: "zh-CN"
    };

    get richEditorOptions() {
        return this._richEditorOptions;
    }

    set richEditorOptions(value) {
        this._richEditorOptions = value;
    }

    get currentUserId(): number {
        let user = this.currentUser;
        if (user == null) {
            return null;
        }

        return user.id;
    }

    get currentUser(): UserSimple {
        let loginData = this._loginData;
        if (loginData == null) {
            return null;
        }

        return this.loginData.user;
    }

    /**
     * 从后台验证保存的TOKEN是否有效, 有效则获取用户数据
     * @returns {Promise<any>}
     */
    loadConfig(): Promise<any> {
        return new Promise<any>((resolve) => {
            let token = this.localStorageService.get<string>("TOKEN");

            if (token == null || token.trim().length === 0) {
                resolve(null);

            } else {
                this.authService.loginByToken(token)
                    .subscribe((loginData) => {
                            // console.log("loginByToken ", token);
                            if (loginData && loginData.result === "SUCCESS") {
                                this.loginData = loginData;

                            } else {
                                this.clearToken();
                                this.loginData = null;
                            }
                            resolve(loginData);

                        }, (error) => {
                            this.clearToken();
                            resolve(null);
                        }
                    );
            }
        });
    }

    /**
     * 退出系统登录
     */
    private doLogout() {
        this._loginData = null;
        this.clearToken();
    }

    /**
     * 清除保存的TOKEN
     */
    private clearToken() {
        HttpGlobalHeader.setToken(null);
        this.localStorageService.remove("TOKEN");
    }
};
