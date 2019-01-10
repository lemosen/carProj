import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {RichEditorModule} from './rich-editor/rich-editor.module';
import {SelectModule} from './select/select.module';
import {SharedPipesModule} from '../pipes/shared-pipes.module';
import {SharedDirectivesModule} from '../directives/shared-directives.module';
import {MultiselectDropdownModule} from 'angular-2-dropdown-multiselect';
import {PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BreadcrumbComponent} from './breadcrumb/breadcrumb.component';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {VerticalMenuComponent} from './menu/vertical-menu/vertical-menu.component';
import {HorizontalMenuComponent} from './menu/horizontal-menu/horizontal-menu.component';
import {SidebarComponent} from './sidebar/sidebar.component';
import {BackTopComponent} from './back-top/back-top.component';
import {FullScreenComponent} from './fullscreen/fullscreen.component';
import {ApplicationsComponent} from './applications/applications.component';
import {MessagesComponent} from './messages/messages.component';
import {UserMenuComponent} from './user-menu/user-menu.component';
import {FlagsMenuComponent} from './flags-menu/flags-menu.component';
import {FavoritesComponent} from './favorites/favorites.component';
import {SideChatComponent} from './side-chat/side-chat.component';
import {RouterModule} from '@angular/router';
import {SelectProvinceModule} from "./selectProvince/select-province.module";
import {SelectProvinceComponent} from "./selectProvince/select-province.component";

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
    suppressScrollX: true
};

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        PerfectScrollbarModule,
        NgbModule.forRoot(),
        MultiselectDropdownModule,
        SharedPipesModule,
        SelectModule,
        RichEditorModule,
        SharedDirectivesModule,
        SelectProvinceModule,
    ],
    declarations: [
        HeaderComponent,
        FooterComponent,
        SidebarComponent,
        VerticalMenuComponent,
        HorizontalMenuComponent,
        BreadcrumbComponent,
        BackTopComponent,
        FullScreenComponent,
        ApplicationsComponent,
        MessagesComponent,
        UserMenuComponent,
        FlagsMenuComponent,
        SideChatComponent,
        FavoritesComponent,
    ],
    exports: [
        SelectModule,
        RichEditorModule,
        HeaderComponent,
        FooterComponent,
        SidebarComponent,
        VerticalMenuComponent,
        HorizontalMenuComponent,
        BreadcrumbComponent,
        BackTopComponent,
        FullScreenComponent,
        ApplicationsComponent,
        MessagesComponent,
        UserMenuComponent,
        FlagsMenuComponent,
        SideChatComponent,
        FavoritesComponent,
        SelectProvinceComponent,
    ],
    providers: [
        {
            provide: PERFECT_SCROLLBAR_CONFIG,
            useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
        }
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedComponentsModule {
}
