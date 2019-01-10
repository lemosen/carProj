import {NgModule} from '@angular/core';
import {LoginComponent} from "./login/login.component";
import {RouterModule, Routes} from "@angular/router";
import {AuthService} from "./auth.service";
import {SharedModule} from '../../shared/shared.module';
import {ComponentsModule} from '../components/components.module';

const routes: Routes = [
    {path: '', component: LoginComponent, pathMatch: 'full'},
    {path: 'login', component: LoginComponent},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [
        LoginComponent,
    ],
    exports: [
        RouterModule
    ],
    providers: [
        AuthService
    ],
})

export class AuthModule {

}
