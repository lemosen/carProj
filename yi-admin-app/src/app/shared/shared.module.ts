import {CUSTOM_ELEMENTS_SCHEMA, ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {TreeModule} from 'ng2-tree';

import {SharedComponentsModule} from './components/shared-components.module';
import {SharedPipesModule} from './pipes/shared-pipes.module';
import {SharedDirectivesModule} from './directives/shared-directives.module';
import {RouterModule} from '@angular/router';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
    imports: [
        TranslateModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        Ng2SmartTableModule,
        TreeModule,
        SharedDirectivesModule,
        SharedPipesModule,
        SharedComponentsModule,
        RouterModule,
    ],
    declarations: [],
    exports: [
        TranslateModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        Ng2SmartTableModule,
        TreeModule,
        SharedDirectivesModule,
        SharedPipesModule,
        SharedComponentsModule,
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class SharedModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SharedModule
        };
    }
}
