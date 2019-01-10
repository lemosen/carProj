import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';

import {NotFoundComponent} from './pages/errors/not-found/not-found.component';

export const routes: Routes = [
    {path: '', redirectTo: 'pages', pathMatch: 'full'},
    {path: 'pages', loadChildren: 'app/pages/pages.module#PagesModule'},
    {path: 'login', loadChildren: 'app/pages/login/login.module#LoginModule'},
    {path: '**', component: NotFoundComponent}
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules,
    enableTracing: false
    // useHash: true
});