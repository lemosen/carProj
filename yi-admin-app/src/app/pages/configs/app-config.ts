import {Injectable} from "@angular/core";

@Injectable()
export class AppConfig {

    // private baseURL: string = "http://remote:8001";   // 测试环境
    // private baseURL: string = "http://192.168.0.19:8082";   // 开发环境
    // private baseURL: string = "http://192.168.0.18:8082";   // 开发环境
    private baseURL: string = "http://localhost:8082";   // 开发环境

    private hostURL: string;
    private imgBaseURL: string;

    constructor() {
        this.hostURL = this.baseURL + "";
        this.imgBaseURL = this.baseURL + "";
    }

    getBase() {
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
