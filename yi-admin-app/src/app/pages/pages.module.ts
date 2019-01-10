import {NgModule} from '@angular/core';
import {routing} from './pages.routing';
import {PagesComponent} from './pages.component';
import {SharedModule} from '../shared/shared.module';
import {SharedPipesModule} from '../shared/pipes/shared-pipes.module';
import {SharedComponentsModule} from '../shared/components/shared-components.module';


@NgModule({
    imports: [
        SharedModule.forRoot(),

        SharedComponentsModule,
        SharedPipesModule,
        routing
    ],
    declarations: [
        PagesComponent,
    ],

})
export class PagesModule {
}
