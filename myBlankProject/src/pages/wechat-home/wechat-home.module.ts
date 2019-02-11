import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { WechatHomePage } from './wechat-home';

@NgModule({
  declarations: [
    WechatHomePage,
  ],
  imports: [
    IonicPageModule.forChild(WechatHomePage),
  ],
})
export class WechatHomePageModule {}
