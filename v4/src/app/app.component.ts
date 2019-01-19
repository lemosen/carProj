import {Component} from '@angular/core';

import {Platform} from '@ionic/angular';
import {SplashScreen} from '@ionic-native/splash-screen/ngx';
import {StatusBar} from '@ionic-native/status-bar/ngx';


@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html'
})
export class AppComponent {
    constructor(
        private platform: Platform,
        private splashScreen: SplashScreen,
        private statusBar: StatusBar
    ) {
        this.initializeApp();
    }

    initializeApp() {
        this.platform.ready().then(() => {
            this.statusBar.styleDefault();

            // document.getElementById('splashShow').style.display = 'none'
        });
        this.platform.backButton.subscribe(e => {
            // console.log(e);
        })
        // this.platform.backButton(() => {
        //     // if (this.backButtonPressed) { //当触发标志为true时，即2秒内双击返回按键则退出APP
        //     //     platform.exitApp();
        //     // } else {
        //     //     this.toastCtrl.create({
        //     //         message: '再按一次退出应用',
        //     //         duration: 2000,
        //     //         position: 'middle'
        //     //     }).present();
        //     //     this.backButtonPressed = true;
        //     //     setTimeout(() => this.backButtonPressed = false, 2000);//2秒内没有再次点击返回则将触发标志标记为false
        //     // }
        // });
    }
}
