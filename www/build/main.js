webpackJsonp([4],{

/***/ 109:
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = 109;

/***/ }),

/***/ 150:
/***/ (function(module, exports, __webpack_require__) {

var map = {
	"../pages/customer/customer.module": [
		273,
		3
	],
	"../pages/login-step1/login-step1.module": [
		274,
		2
	],
	"../pages/login-step2/login-step2.module": [
		275,
		1
	],
	"../pages/wechat-home/wechat-home.module": [
		276,
		0
	]
};
function webpackAsyncContext(req) {
	var ids = map[req];
	if(!ids)
		return Promise.reject(new Error("Cannot find module '" + req + "'."));
	return __webpack_require__.e(ids[1]).then(function() {
		return __webpack_require__(ids[0]);
	});
};
webpackAsyncContext.keys = function webpackAsyncContextKeys() {
	return Object.keys(map);
};
webpackAsyncContext.id = 150;
module.exports = webpackAsyncContext;

/***/ }),

/***/ 151:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AboutPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(24);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var AboutPage = /** @class */ (function () {
    function AboutPage(navCtrl) {
        this.navCtrl = navCtrl;
        this.pet = 'q';
    }
    AboutPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-about',template:/*ion-inline-start:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\about\about.html"*/'<ion-header>\n  <ion-navbar>\n    <ion-title>\n      我的学员\n    </ion-title>\n  </ion-navbar>\n</ion-header>\n\n<ion-content>\n  <div padding>\n    <ion-segment [(ngModel)]="pet">\n      <ion-segment-button value="q">\n        报名\n      </ion-segment-button>\n      <ion-segment-button value="w">\n        科目一\n      </ion-segment-button>\n      <ion-segment-button value="e">\n        科目二\n      </ion-segment-button>\n      <ion-segment-button value="r">\n        科目三\n      </ion-segment-button>\n      <ion-segment-button value="t">\n        科目四\n      </ion-segment-button>\n      <ion-segment-button value="y">\n        领证\n      </ion-segment-button>\n    </ion-segment>\n  </div>\n  <ion-list>\n    <ion-item>\n      <ion-avatar item-start>\n        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547631970422&di=5d6c7847832fed9a625cdc3578479cd2&imgtype=0&src=http%3A%2F%2Flife.southmoney.com%2Ftuwen%2FUploadFiles_6871%2F201808%2F20180808151217516.jpg">\n      </ion-avatar>\n      <h2>学员1</h2>\n      <h3>13670000000</h3>\n      <p>成绩100<span style="float: right">2018-12-10</span></p>\n    </ion-item>\n    <ion-item>\n      <ion-avatar item-start>\n        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547631970422&di=5d6c7847832fed9a625cdc3578479cd2&imgtype=0&src=http%3A%2F%2Flife.southmoney.com%2Ftuwen%2FUploadFiles_6871%2F201808%2F20180808151217516.jpg">\n      </ion-avatar>\n      <h2>学员2</h2>\n      <h3>13670000000</h3>\n      <p>成绩100<span style="float: right">2018-12-10</span></p>\n    </ion-item>\n    <ion-item>\n      <ion-avatar item-start>\n        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547631970422&di=5d6c7847832fed9a625cdc3578479cd2&imgtype=0&src=http%3A%2F%2Flife.southmoney.com%2Ftuwen%2FUploadFiles_6871%2F201808%2F20180808151217516.jpg">\n      </ion-avatar>\n      <h2>学员3</h2>\n      <h3>13670000000</h3>\n      <p>成绩100<span style="float: right">2018-12-10</span></p>\n    </ion-item>\n  </ion-list>\n  <div class="flex-row flex-center" style="    width: 100%;\n    position: fixed;\n    text-align: center;\n    flex-wrap: wrap;\n    bottom: 0;">\n    <button ion-button>添加学员</button>\n    <button ion-button>邀请学员</button>\n  </div>\n</ion-content>\n'/*ion-inline-end:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\about\about.html"*/
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* NavController */]])
    ], AboutPage);
    return AboutPage;
}());

//# sourceMappingURL=about.js.map

/***/ }),

/***/ 152:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ContactPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(24);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ContactPage = /** @class */ (function () {
    function ContactPage(navCtrl) {
        this.navCtrl = navCtrl;
    }
    ContactPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-contact',template:/*ion-inline-start:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\contact\contact.html"*/'<ion-header>\n  <ion-navbar>\n    <ion-title>\n      推广\n    </ion-title>\n    <ion-buttons end>\n      <button ion-button>钱包</button>\n\n    </ion-buttons>\n  </ion-navbar>\n</ion-header>\n\n<ion-content>\n  <div class="flex-col flex-center" style="padding:3px 0;background-color: white">\n    <div style="text-align: center ;margin: 20px">\n      <div class="common-title" style="color: red">99999999</div>\n      <div class="common-sub-title">累计收入（元）</div>\n    </div>\n\n    <div class="flex-row flex-center" style="padding:20px 0;text-align: center">\n      <div style="width: 50%">\n        <div class="common-sub-title" style="color: red">6666</div>\n        <div class="common-sub-title">今日收益（元）</div>\n      </div>\n      <div>|</div>\n      <div style="width: 50%">\n        <div class="common-sub-title" style="color: red">888888</div>\n        <div class="common-sub-title">当月收益（元）</div>\n\n      </div>\n    </div>\n\n    <div class="flex-row flex-center" style="padding:20px 0;text-align: center;">\n      <div style="width: 50%" class="common-sub-title">上月收益 <span  style="color: red">186186</span>元</div>\n      <div>|</div>\n      <div style="width: 50%" class="common-sub-title">收益明细</div>\n    </div>\n    <button ion-item >收益龙虎榜</button>\n    <button ion-item >邀请教练</button>\n    <button ion-item >邀请学员</button>\n\n  </div>\n\n</ion-content>\n'/*ion-inline-end:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\contact\contact.html"*/
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* NavController */]])
    ], ContactPage);
    return ContactPage;
}());

//# sourceMappingURL=contact.js.map

/***/ }),

/***/ 153:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HomePage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(24);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var HomePage = /** @class */ (function () {
    function HomePage(navCtrl) {
        this.navCtrl = navCtrl;
    }
    HomePage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-home',template:/*ion-inline-start:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\home\home.html"*/'<ion-content>\n  <ion-slides style="height: 200px;" pager>\n\n    <ion-slide style="background-color: #b3b3ef">\n      <h2>banner 1</h2>\n    </ion-slide>\n\n    <ion-slide style="background-color: #b3b3ef">\n      <h2>banner 2</h2>\n    </ion-slide>\n\n    <ion-slide style="background-color: #b3b3ef">\n      <h2>banner 3</h2>\n    </ion-slide>\n\n  </ion-slides>\n  <div class="flex-row" style=" text-align: center;   flex-wrap: wrap;">\n    <div class="padding common-sub-title" style="width: 33%"> <ion-icon name="car"></ion-icon><div style="margin-top: 0">设备</div></div>\n    <div class="padding common-sub-title" style="width: 33%"> <ion-icon name="open"></ion-icon><div style="margin-top: 0">教学</div></div>\n    <div class="padding common-sub-title" style="width: 33%"> <ion-icon name="calculator"></ion-icon><div style="margin-top: 0">成绩</div></div>\n    <div class="padding common-sub-title" style="width: 33%"> <ion-icon name="american-football"></ion-icon><div style="margin-top: 0">连接</div></div>\n    <div class="padding common-sub-title" style="width: 33%"> <ion-icon name="globe"></ion-icon><div style="margin-top: 0">培训</div></div>\n    <div class="padding common-sub-title" style="width: 33%"> <ion-icon name="analytics"></ion-icon><div style="margin-top: 0">分析</div></div>\n  </div>\n  <div class="flex-row" style="    width: 100%;\n    position: fixed;\n    text-align: center;\n    flex-wrap: wrap;\n    bottom: 10%;">\n    <div class="padding common-sub-title" style="width: 25%"><ion-icon name="clipboard"></ion-icon><div style="margin-top: 0">考场</div></div>\n    <div class="padding common-sub-title" style="width: 25%"><ion-icon name="albums"></ion-icon><div style="margin-top: 0">参数</div></div>\n    <div class="padding common-sub-title" style="width: 25%"><ion-icon name="git-merge"></ion-icon><div style="margin-top: 0">路线</div></div>\n    <div class="padding common-sub-title" style="width: 25%"><ion-icon name="document"></ion-icon><div style="margin-top: 0">信息</div></div>\n    <div class="padding common-sub-title" style="width: 25%"><ion-icon name="folder"></ion-icon><div style="margin-top: 0">数据</div></div>\n    <div class="padding common-sub-title" style="width: 25%"><ion-icon name="build"></ion-icon><div style="margin-top: 0">设置</div></div>\n    <div class="padding common-sub-title" style="width: 25%"><ion-icon name="build"></ion-icon><div style="margin-top: 0">设置</div></div>\n    <div class="padding common-sub-title" style="width: 25%"><ion-icon name="logo-css3"></ion-icon><div style="margin-top: 0">发布</div></div>\n  </div>\n</ion-content>\n'/*ion-inline-end:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\home\home.html"*/
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* NavController */]])
    ], HomePage);
    return HomePage;
}());

//# sourceMappingURL=home.js.map

/***/ }),

/***/ 197:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoginPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(24);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


/**
 * Generated class for the LoginPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
var LoginPage = /** @class */ (function () {
    function LoginPage(navCtrl, navParams) {
        this.navCtrl = navCtrl;
        this.navParams = navParams;
    }
    LoginPage.prototype.ionViewDidLoad = function () {
        console.log('ionViewDidLoad LoginPage');
    };
    LoginPage.prototype.step = function () {
        this.navCtrl.push('LoginStep1Page');
        // this.navCtrl.setRoot(TabsPage)
    };
    LoginPage.prototype.wechatModel = function () {
        this.navCtrl.push('WechatHomePage');
    };
    LoginPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-login',template:/*ion-inline-start:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\login\login.html"*/'<!--\n  Generated template for the LoginPage page.\n\n  See http://ionicframework.com/docs/components/#navigation for more info on\n  Ionic pages and navigation.\n-->\n\n\n<ion-content padding>\n  <div style="height: 100%" class="flex-col flex-center flex-item-center">\n\n\n    <div class="flex-col  flex-item-center">\n      <div class="padding common-title">学考一点通</div>\n      <div class="padding common-sub-title">学理论省时 练车更省力</div>\n      <div class="common-hr" style="height: 2px!important; width: 100%;background-color:#d5d5ff!important;"></div>\n      <ion-item class="margin">\n        <ion-label>手机号</ion-label>\n        <ion-input></ion-input>\n      </ion-item>\n\n      <button ion-button (click)="step()">下一步</button>\n      <button ion-button (click)="wechatModel()">微信端原型（正式产品没有此按钮）</button>\n    </div>\n  </div>\n</ion-content>\n'/*ion-inline-end:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\login\login.html"*/,
        }),
        __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* NavController */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* NavController */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* NavParams */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* NavParams */]) === "function" && _b || Object])
    ], LoginPage);
    return LoginPage;
    var _a, _b;
}());

//# sourceMappingURL=login.js.map

/***/ }),

/***/ 198:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TabsPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__about_about__ = __webpack_require__(151);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__contact_contact__ = __webpack_require__(152);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__home_home__ = __webpack_require__(153);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__customer_customer__ = __webpack_require__(99);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var TabsPage = /** @class */ (function () {
    function TabsPage() {
        this.tab1Root = __WEBPACK_IMPORTED_MODULE_3__home_home__["a" /* HomePage */];
        this.tab2Root = __WEBPACK_IMPORTED_MODULE_1__about_about__["a" /* AboutPage */];
        this.tab3Root = __WEBPACK_IMPORTED_MODULE_2__contact_contact__["a" /* ContactPage */];
        this.tab4Root = __WEBPACK_IMPORTED_MODULE_4__customer_customer__["a" /* CustomerPage */];
    }
    TabsPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({template:/*ion-inline-start:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\tabs\tabs.html"*/'<ion-tabs>\n  <ion-tab [root]="tab1Root" tabTitle="教务" tabIcon="home"></ion-tab>\n  <ion-tab [root]="tab2Root" tabTitle="学员" tabIcon="information-circle"></ion-tab>\n  <ion-tab [root]="tab3Root" tabTitle="推广" tabIcon="contacts"></ion-tab>\n  <ion-tab [root]="tab4Root" tabTitle="我的" tabIcon="contact"></ion-tab>\n</ion-tabs>\n'/*ion-inline-end:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\tabs\tabs.html"*/
        }),
        __metadata("design:paramtypes", [])
    ], TabsPage);
    return TabsPage;
}());

//# sourceMappingURL=tabs.js.map

/***/ }),

/***/ 199:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser_dynamic__ = __webpack_require__(200);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__app_module__ = __webpack_require__(222);


Object(__WEBPACK_IMPORTED_MODULE_0__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_1__app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 222:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__ = __webpack_require__(31);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_ionic_angular__ = __webpack_require__(24);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__app_component__ = __webpack_require__(265);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__pages_about_about__ = __webpack_require__(151);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__pages_contact_contact__ = __webpack_require__(152);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__pages_home_home__ = __webpack_require__(153);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__pages_tabs_tabs__ = __webpack_require__(198);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__ionic_native_status_bar__ = __webpack_require__(193);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__ionic_native_splash_screen__ = __webpack_require__(196);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__pages_login_login__ = __webpack_require__(197);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__pages_customer_customer__ = __webpack_require__(99);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};












var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["I" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_3__app_component__["a" /* MyApp */],
                __WEBPACK_IMPORTED_MODULE_4__pages_about_about__["a" /* AboutPage */],
                __WEBPACK_IMPORTED_MODULE_5__pages_contact_contact__["a" /* ContactPage */],
                __WEBPACK_IMPORTED_MODULE_6__pages_home_home__["a" /* HomePage */],
                __WEBPACK_IMPORTED_MODULE_7__pages_tabs_tabs__["a" /* TabsPage */],
                __WEBPACK_IMPORTED_MODULE_10__pages_login_login__["a" /* LoginPage */],
                __WEBPACK_IMPORTED_MODULE_11__pages_customer_customer__["a" /* CustomerPage */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_2_ionic_angular__["c" /* IonicModule */].forRoot(__WEBPACK_IMPORTED_MODULE_3__app_component__["a" /* MyApp */], {
                    mode: 'ios',
                    backButtonText: '',
                    tabsHideOnSubPages: 'true'
                }, {
                    links: [
                        { loadChildren: '../pages/customer/customer.module#CustomerPageModule', name: 'CustomerPage', segment: 'customer', priority: 'low', defaultHistory: [] },
                        { loadChildren: '../pages/login-step1/login-step1.module#LoginStep1PageModule', name: 'LoginStep1Page', segment: 'login-step1', priority: 'low', defaultHistory: [] },
                        { loadChildren: '../pages/login-step2/login-step2.module#LoginStep2PageModule', name: 'LoginStep2Page', segment: 'login-step2', priority: 'low', defaultHistory: [] },
                        { loadChildren: '../pages/wechat-home/wechat-home.module#WechatHomePageModule', name: 'WechatHomePage', segment: 'wechat-home', priority: 'low', defaultHistory: [] }
                    ]
                }),
            ],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_2_ionic_angular__["a" /* IonicApp */]],
            entryComponents: [
                __WEBPACK_IMPORTED_MODULE_3__app_component__["a" /* MyApp */],
                __WEBPACK_IMPORTED_MODULE_4__pages_about_about__["a" /* AboutPage */],
                __WEBPACK_IMPORTED_MODULE_5__pages_contact_contact__["a" /* ContactPage */],
                __WEBPACK_IMPORTED_MODULE_6__pages_home_home__["a" /* HomePage */],
                __WEBPACK_IMPORTED_MODULE_7__pages_tabs_tabs__["a" /* TabsPage */],
                __WEBPACK_IMPORTED_MODULE_10__pages_login_login__["a" /* LoginPage */],
                __WEBPACK_IMPORTED_MODULE_11__pages_customer_customer__["a" /* CustomerPage */]
            ],
            providers: [
                __WEBPACK_IMPORTED_MODULE_8__ionic_native_status_bar__["a" /* StatusBar */],
                __WEBPACK_IMPORTED_MODULE_9__ionic_native_splash_screen__["a" /* SplashScreen */],
                { provide: __WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ErrorHandler */], useClass: __WEBPACK_IMPORTED_MODULE_2_ionic_angular__["b" /* IonicErrorHandler */] }
            ]
        })
    ], AppModule);
    return AppModule;
}());

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ 265:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MyApp; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(24);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__ionic_native_status_bar__ = __webpack_require__(193);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ionic_native_splash_screen__ = __webpack_require__(196);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__pages_login_login__ = __webpack_require__(197);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var MyApp = /** @class */ (function () {
    function MyApp(platform, statusBar, splashScreen) {
        this.rootPage = __WEBPACK_IMPORTED_MODULE_4__pages_login_login__["a" /* LoginPage */];
        platform.ready().then(function () {
            // Okay, so the platform is ready and our plugins are available.
            // Here you can do any higher level native things you might need.
            statusBar.styleDefault();
            splashScreen.hide();
        });
    }
    MyApp = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({template:/*ion-inline-start:"F:\IdeaProjects\crgk1\myBlankProject\src\app\app.html"*/'<ion-nav [root]="rootPage"></ion-nav>\n'/*ion-inline-end:"F:\IdeaProjects\crgk1\myBlankProject\src\app\app.html"*/
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["g" /* Platform */], __WEBPACK_IMPORTED_MODULE_2__ionic_native_status_bar__["a" /* StatusBar */], __WEBPACK_IMPORTED_MODULE_3__ionic_native_splash_screen__["a" /* SplashScreen */]])
    ], MyApp);
    return MyApp;
}());

//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ 99:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CustomerPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(24);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


/**
 * Generated class for the CustomerPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
var CustomerPage = /** @class */ (function () {
    function CustomerPage(navCtrl, navParams) {
        this.navCtrl = navCtrl;
        this.navParams = navParams;
    }
    CustomerPage.prototype.ionViewDidLoad = function () {
        console.log('ionViewDidLoad CustomerPage');
    };
    CustomerPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-customer',template:/*ion-inline-start:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\customer\customer.html"*/'<!--\n  Generated template for the CustomerPage page.\n\n  See http://ionicframework.com/docs/components/#navigation for more info on\n  Ionic pages and navigation.\n-->\n<ion-header>\n\n  <ion-navbar>\n    <ion-title>我的</ion-title>\n  </ion-navbar>\n\n</ion-header>\n\n\n<ion-content >\n  <div class="flex-col flex-center" style="background-color: white">\n    <ion-item>\n      <ion-avatar item-start>\n        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547631970422&di=5d6c7847832fed9a625cdc3578479cd2&imgtype=0&src=http%3A%2F%2Flife.southmoney.com%2Ftuwen%2FUploadFiles_6871%2F201808%2F20180808151217516.jpg">\n      </ion-avatar>\n      <h2>教练1</h2>\n      <h3>13670000000</h3>\n    </ion-item>\n    <button ion-item >个人信息</button>\n    <button ion-item >事业合伙人</button>\n    <button ion-item >推广二维码</button>\n    <button ion-item >操作指引</button>\n    <button ion-item >关于我们</button>\n  </div>\n</ion-content>\n'/*ion-inline-end:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\customer\customer.html"*/,
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* NavController */], __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* NavParams */]])
    ], CustomerPage);
    return CustomerPage;
}());

//# sourceMappingURL=customer.js.map

/***/ })

},[199]);
//# sourceMappingURL=main.js.map