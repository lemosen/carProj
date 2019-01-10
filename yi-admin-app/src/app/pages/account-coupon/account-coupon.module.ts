import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AccountCouponService} from '../../services/account-coupon.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddAccountCouponComponent} from './add-account-coupon/add-account-coupon.component';
import {EditAccountCouponComponent} from './edit-account-coupon/edit-account-coupon.component';
import {ViewAccountCouponComponent} from './view-account-coupon/view-account-coupon.component';
import {ListAccountCouponComponent} from './list-account-coupon/list-account-coupon.component';
import {FormAccountCouponComponent} from './form-account-coupon/form-account-coupon.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAccountCouponComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAccountCouponComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAccountCouponComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAccountCouponComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddAccountCouponComponent,
        EditAccountCouponComponent,
        ViewAccountCouponComponent,
        ListAccountCouponComponent,
        FormAccountCouponComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [AccountCouponService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountCouponModule {
}
