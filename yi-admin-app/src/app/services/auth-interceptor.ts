import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {HttpGlobalHeader} from "../pages/configs/http-global-header";

/**
 * 监控每次调用后台服务后, 自动刷新 JWT 令牌
 */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor() {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // if (HttpGlobalHeader.getToken()) {
        //   req = req.clone({
        //     setHeaders: {
        //       token: HttpGlobalHeader.getToken()
        //     }
        //   });
        // }

        return next.handle(req).map((event) => {
            if (event instanceof HttpResponse && event.status == 200) {
                let token = event.headers.get("token");
                if (token && token.trim().length > 0) {
                    HttpGlobalHeader.setToken(token);
                }
            }

            return event;
        });
    }
}
