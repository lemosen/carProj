import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {TabsPage} from './tabs.page';
import {HomePage} from '../home/home.page';
import {ShoppingCartPage} from '../shopping-cart/shopping-cart.page';
import {LoveLifePage} from '../love-life/love-life.page';
import {CustomerCenterPage} from '../customer-center/customer-center.page';
import {CategoryPage} from "../category/category.page";

const routes: Routes = [
    {
        path: 'tabs',
        component: TabsPage,
        children: [
            {
                path: '',
                redirectTo: '/tabs/(home:home)',
                pathMatch: 'full',
            },
            {
                path: 'home',
                outlet: 'home',
                component: HomePage
            },
            {
                path: 'category',
                outlet: 'category',
                component: CategoryPage
            },
            {
                path: 'shoppingCart',
                outlet: 'shoppingCart',
                component: ShoppingCartPage,
            },
            {
                path: 'loveLife',
                outlet: 'loveLife',
                component: LoveLifePage
            },
            {
                path: 'customerCenter',
                outlet: 'customerCenter',
                component: CustomerCenterPage
            }
        ]
    },
    {
        path: '',
        redirectTo: '/tabs/(home:home)',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TabsPageRoutingModule {
}
