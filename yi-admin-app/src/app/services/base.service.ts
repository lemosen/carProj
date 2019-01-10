import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams,} from "@angular/common/http";
import {saveAs} from "file-saver";
import {AppConfig} from '../pages/configs/app-config';
import {HttpGlobalHeader} from '../pages/configs/http-global-header';
import {ObjectUtils} from "../shared/utils/ObjectUtils";
import {ChooseQueryBase} from "../pages/models/domain/object-choose-query";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";
import {HttpObserve} from "@angular/common/http/src/client";

const encodeFormData = ObjectUtils.encodeFormData;

const isDate = ObjectUtils.isDate;
const isArray = ObjectUtils.isArray;
const isObject = ObjectUtils.isObject;
const isEmptyObject = ObjectUtils.isEmptyObject;
const isEmpty = ObjectUtils.isEmpty;


/**
 * Construct an instance of `HttpRequestOptions<T>` from a source `HttpMethodOptions` and
 * the given `body`. Basically, this clones the object and adds the body.
 */
function addBody<T>(
    options: {
        headers?: HttpHeaders | { [header: string]: string | string[] },
        observe?: HttpObserve,
        params?: HttpParams | { [param: string]: string | string[] },
        reportProgress?: boolean,
        responseType?: 'arraybuffer' | 'blob' | 'json' | 'text',
        withCredentials?: boolean,
    },
    body: T | null): any {
    if (body == null) return options;
    return {
        body,
        headers: options.headers,
        observe: options.observe,
        params: options.params,
        reportProgress: options.reportProgress,
        responseType: options.responseType,
        withCredentials: options.withCredentials,
    };
}

@Injectable()
export abstract class BaseService {
    protected httpGlobalHeader: HttpGlobalHeader;

    constructor(protected http: HttpClient, protected appConfig: AppConfig, protected nameSpace: string) {
        this.httpGlobalHeader = HttpGlobalHeader.getInstance();
    }
    /*dubbger;*/
    query(pageQuery: any): Observable<any> {
        return this.postData("query", pageQuery);
    }

    querySimple(pageQuery: any): Observable<any> {
        return this.postData("querySimple", pageQuery);
    }

    queryObjectsByConditions(chooseQuery: ChooseQueryBase): Observable<any> {
        return this.postData("queryObjectsByConditions", chooseQuery);
    }

    getAll(param?: any): Observable<any> {
        return this.postFormData("getAll", param);
    }

    getById(id: any): Observable<any> {
        return this.getByField("getById", "id", id);
    }

    getBoById(id: any): Observable<any> {
        return this.getByField("getBoById", "id", id);
    }

    getVoById(id: any): Observable<any> {
        return this.getByField("getById", "id", id);
    }

    getSimpleById(id: any): Observable<any> {
        return this.getByField("getSimpleById", "id", id);
    }

    removeById(id: any): Observable<any> {
        return this.removeByField("removeById", "id", id);
    }

    add(record: any): Observable<any> {
        return this.postData("add", record);
    }

    update(record: any): Observable<any> {
        return this.postData("update", record);
    }

    updateState(param) {
        return this.postFormData("updateState", param);
    }

    updateShelf(param) {
        return this.postFormData("updateShelf",param);
    }

    postData(func: string, param: any): Observable<any> {
        let body;
        if (param) {
            if (param.toJsonString) {
                body = param.toJsonString();
            } else {
                body = JSON.stringify(param);
            }
        }

        let url = `${this.appConfig.getBase()}/${this.nameSpace}/${func}`;
        return this.http.request("POST", url, addBody(this.getJsonOptions(), body))
            .catch(this.handleError);
    }

    postFormData(func: string, params: any): Observable<any> {
        let body = encodeFormData(params);

        let url = `${this.appConfig.getBase()}/${this.nameSpace}/${func}`;
        return this.http.request("POST", url, addBody(this.getFormOptions(), body))
            .catch(this.handleError);
    }

    getByField(func: string, paramName: string, paramValue: any): Observable<any> {
        let url = `${this.appConfig.getBase()}/${this.nameSpace}/${func}`;
        if (paramName != null) {
            if (url.indexOf("?") === -1) url += "?";
            url += encodeURIComponent(paramName) + "=" + encodeURIComponent(paramValue);
        }
        return this.http.request("GET", url, this.getFormOptions())
            .catch(this.handleError);
    }

    getByParams(func: string, params?: any): Observable<any> {
        let url = `${this.appConfig.getBase()}/${this.nameSpace}/${func}`;

        if (params != null) {
            if (url.indexOf("?") === -1) url += "?";
            url += encodeFormData(params);
        }
        return this.http.request("GET", url, this.getFormOptions())
            .catch(this.handleError);
    }

    removeByField(func: string, paramName: string, paramValue: any): Observable<any> {
        let url = `${this.appConfig.getBase()}/${this.nameSpace}/${func}`;

        if (paramName != null) {
            if (url.indexOf("?") === -1) url += "?";
            url += encodeURIComponent(paramName) + "=" + encodeURIComponent(paramValue);
        }
        return this.http.request("GET", url, this.getFormOptions())
            .catch(this.handleError);
    }

    removeByParams(func: string, params: any): Observable<any> {
        let url = `${this.appConfig.getBase()}/${this.nameSpace}/${func}`;

        if (params != null) {
            if (url.indexOf("?") === -1) url += "?";
            url += encodeFormData(params);
        }

        return this.http.request("GET", url, this.getFormOptions())
            .catch(this.handleError);
    }

    /**
     * 使用 GET / POST 方式下载附件, url 如果不以 "/" 开始则自动加上服务的基础路径
     *
     * @param {string} method
     * @param {string} filename
     * @param {string} url
     * @param params
     */
    download(method: string, filename: string, url: string, params: any) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            if (!url.startsWith("/")) {
                url = this.nameSpace + "/" + url;
            }

            url = `${this.appConfig.getBase()}/${url}`;
        }

        let request: Observable<Blob>;
        if (method === "GET") {
            if (params != null) {
                if (url.indexOf("?") === -1) url += "?";
                url += encodeFormData(params);
            }

            request = this.http.request("GET", url, {
                    headers: this.httpGlobalHeader.jsonHeaders,
                    withCredentials: false,
                    responseType: "blob"
                }
            );

        } else {
            let body;
            if (params) {
                if (params.toJsonString) {
                    body = params.toJsonString();
                } else {
                    body = JSON.stringify(params);
                }
            }

            request = this.http.request("POST", url, {
                    body: body,
                    headers: this.httpGlobalHeader.jsonHeaders,
                    withCredentials: false,
                    responseType: "blob"
                }
            );
        }

        request.subscribe(blob => {
            saveAs(blob, filename);
        });
    }

    // protected promiseHandleError(error: any): Promise<any> {
    //   console.error('An error occurred', error.message);
    //   return Promise.reject(error.message || error.message);
    // }

    /**
     * 处理异常
     * @param error
     * @returns {ErrorObservable}
     */
    protected handleError(error: HttpErrorResponse) {
        if (error.error instanceof ErrorEvent) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('后端服务调用时发生错误, 原因=' + error.error.message);

        } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong,
            console.error("后端服务返回错误, 状态码=" + error.status, error.error);
        }

        // return an ErrorObservable with a user-facing error message
        return new ErrorObservable();
    };

    protected getFormOptions() {
        return this.httpGlobalHeader.formOptions;
    }

    protected getJsonOptions() {
        return this.httpGlobalHeader.jsonOptions;
    }
}
