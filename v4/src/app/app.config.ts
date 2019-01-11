import {Injectable} from "@angular/core";

@Injectable()
export class AppConfig {

    // baseURL: string = "http://remote:8001";    // 测试环境
    // baseURL: string = "http://192.168.0.62:8081/";   // 开发环境
    baseURL: string = "http://test.h5server.bluerice.cn/";      // 开发环境
    // baseURL: string = "http://test.app.my11mall.com/";      // 开发环境
    // baseURL: string = "http://h5server.bluerice.cn/";      // 开发环境
    // baseURL: string = "/app/";                       // 开发环境
    kuaidi100: string = "https://m.kuaidi100.com/";     // 开发环境

    wechatAppId = 'wx4cfd5bff2944b9d4';

    // wechatAutoLogin=true;
    wechatAutoLogin=false;

    hostURL: string;
    imgBaseURL: string;

    getBase(): string {
        return this.baseURL;
    }

    getAttachmentBase() {
        return this.baseURL + '/attachment/';
    }

    getHost() {
        return this.hostURL;
    }

    getImgBase() {
        return this.imgBaseURL;
    }

}
