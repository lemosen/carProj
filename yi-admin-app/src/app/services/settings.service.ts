import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs/BehaviorSubject";

/**
 * 在下一个渲染周期执行回调函数
 * @param cb
 */
function nextTick(cb) {
    setTimeout(cb, 0);
}

@Injectable()
export class SettingsService {
    public user: any;
    public app: any;
    public layout: any;


    public onLogout: BehaviorSubject<any> = new BehaviorSubject(null);

    constructor() {
        // User Settings
        // -----------------------------------
        this.user = {
            name: '管理员',
            avatarImg: 'assets/img/user/13.jpg'
        };

        // App Settings
        // -----------------------------------
        this.app = {
            name: '管理系统',
            customName: 'i管理系统',
            description: '有限公司',
            year: ((new Date()).getFullYear())
        };

    }

    /**
     * 发布退出登录事件
     */
    emitLogout() {
        this.onLogout.next(true);
    }

    getAppSetting(name) {
        return name ? this.app[name] : this.app;
    }

    getUserSetting(name) {
        return name ? this.user[name] : this.user;
    }

    getLayoutSetting(name) {
        return name ? this.layout[name] : this.layout;
    }

    setAppSetting(name, value) {
        nextTick(() => {
            if (typeof this.app[name] !== 'undefined') {
                this.app[name] = value;
            }
        });
        return this.app[name];
    }

    setUserSetting(name, value) {
        nextTick(() => {
            if (typeof this.user[name] !== 'undefined') {
                this.user[name] = value;
            }
        });

        return this.user[name];
    }

    /**
     * 修改layout配置, 返回原来的设置值
     * @param name
     * @param value
     * @returns {any}
     */
    setLayoutSetting(name, value) {
        nextTick(() => {
            if (typeof this.layout[name] !== 'undefined') {
                this.layout[name] = value;
            }
        });

        return this.layout[name];
    }

    toggleLayoutSetting(name) {
        return this.setLayoutSetting(name, !this.getLayoutSetting(name));
    }

}
