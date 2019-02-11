import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { LoginStep1Page } from './login-step1';

@NgModule({
  declarations: [
    LoginStep1Page,
  ],
  imports: [
    IonicPageModule.forChild(LoginStep1Page),
  ],
})
export class LoginStep1PageModule {}
