import {IonicModule} from '@ionic/angular';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {TabsPageRoutingModule} from './tabs.router.module';

import {TabsPage} from './tabs.page';
import {ShoppingCartPageModule} from '../shopping-cart/shopping-cart.module';
import {CategoryPageModule} from '../category/category.module';
import {HomePageModule} from '../home/home.module';
import {CustomerCenterPageModule} from '../customer-center/customer-center.module';
import {ComponentsModule} from "../components/components.module";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        TabsPageRoutingModule,
        HomePageModule,
        CategoryPageModule,
        ShoppingCartPageModule,
        ComponentsModule,
        CustomerCenterPageModule
    ],
    declarations: [TabsPage]
})
export class TabsPageModule {
}
