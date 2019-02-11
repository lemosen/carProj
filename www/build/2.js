webpackJsonp([2],{

/***/ 274:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginStep1PageModule", function() { return LoginStep1PageModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(24);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__login_step1__ = __webpack_require__(277);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var LoginStep1PageModule = /** @class */ (function () {
    function LoginStep1PageModule() {
    }
    LoginStep1PageModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["I" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_2__login_step1__["a" /* LoginStep1Page */],
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["d" /* IonicPageModule */].forChild(__WEBPACK_IMPORTED_MODULE_2__login_step1__["a" /* LoginStep1Page */]),
            ],
        })
    ], LoginStep1PageModule);
    return LoginStep1PageModule;
}());

//# sourceMappingURL=login-step1.module.js.map

/***/ }),

/***/ 277:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoginStep1Page; });
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
 * Generated class for the LoginStep1Page page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
var LoginStep1Page = /** @class */ (function () {
    function LoginStep1Page(navCtrl, navParams) {
        this.navCtrl = navCtrl;
        this.navParams = navParams;
    }
    LoginStep1Page.prototype.ionViewDidLoad = function () {
        console.log('ionViewDidLoad LoginStep1Page');
    };
    LoginStep1Page.prototype.step1 = function () {
        this.navCtrl.push('LoginStep2Page');
    };
    LoginStep1Page = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-login-step1',template:/*ion-inline-start:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\login-step1\login-step1.html"*/'<!--\n  Generated template for the LoginStep1Page page.\n\n  See http://ionicframework.com/docs/components/#navigation for more info on\n  Ionic pages and navigation.\n-->\n<ion-header>\n\n  <ion-navbar>\n    <ion-title>用户注册</ion-title>\n  </ion-navbar>\n\n</ion-header>\n\n\n<ion-content >\n  <div  class="flex-col flex-center flex-item-center">\n    <ion-item >\n      <ion-label>手机号</ion-label>\n      <ion-input></ion-input>\n    </ion-item>\n    <ion-item >\n      <ion-label>验证码</ion-label>\n      <ion-input></ion-input>\n      <button item-right ion-button>发送验证码</button>\n    </ion-item>\n    <ion-item >\n      <ion-label>密码</ion-label>\n      <ion-input></ion-input>\n    </ion-item>\n    <ion-item >\n      <ion-label>确认密码</ion-label>\n      <ion-input></ion-input>\n    </ion-item>\n    <button ion-button (click)="step1()">下一步</button>\n  </div>\n\n</ion-content>\n'/*ion-inline-end:"F:\IdeaProjects\crgk1\myBlankProject\src\pages\login-step1\login-step1.html"*/,
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* NavController */], __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* NavParams */]])
    ], LoginStep1Page);
    return LoginStep1Page;
}());

//# sourceMappingURL=login-step1.js.map

/***/ })

});
//# sourceMappingURL=2.js.map