import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';

import {CalendarModule} from 'angular-calendar';

import {routing} from './app.routing';
import {AppSettings} from './app.settings';

import {AppComponent} from './app.component';
import {NotFoundComponent} from './pages/errors/not-found/not-found.component';
import {SharedModule} from './shared/shared.module';
import {NgbProgressbarModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {LocalStorageModule, LocalStorageService} from 'angular-2-local-storage';
import {AuthService} from './pages/auth/auth.service';
import {AppConfig} from './pages/configs/app-config';
import {AppStorage} from './pages/configs/app-storage';
import {AttachmentService} from './services/attachment.service';
import {DeptService} from './services/dept.service';
import {UserService} from './services/user.service';
import {ToastrModule} from 'ngx-toastr';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';



export function createTranslateHttpLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, '/assets/i18n/', '.json');
}

export function onAppInit(appStorage: AppStorage) {
    // 应用初始化, 到后台检查上次保存的TOKEN, 有效则自动登录
    return () => appStorage.loadConfig();
}

@NgModule({
    declarations: [
        AppComponent,
        NotFoundComponent,
    ],
    imports: [
        HttpClientModule,

        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: (createTranslateHttpLoader),
                deps: [HttpClient]
            }
        }),
        LocalStorageModule.withConfig({
            prefix: 'yi-admin',
            storageType: 'localStorage'
        }),
        BrowserModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
        SharedModule.forRoot(),
        // AgmCoreModule.forRoot({
        //     apiKey: 'AIzaSyDe_oVpi9eRSN99G4o6TwVjJbFBNr58NxE'
        // }),
        NgbProgressbarModule.forRoot(),
        CalendarModule.forRoot(),
        routing
    ],
    providers: [
        AppSettings,
        LocalStorageService,
        AppConfig,
        AuthService,
        AppStorage,

        AttachmentService,
        DeptService,
        UserService,

        // {
        //     provide: APP_INITIALIZER, useFactory: onAppInit, multi: true,
        //     deps: [AppStorage]
        // },
        // {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
