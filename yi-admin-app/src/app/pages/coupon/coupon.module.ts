


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {CouponService} from '../../services/coupon.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddCouponComponent} from './add-coupon/add-coupon.component';
import {EditCouponComponent} from './edit-coupon/edit-coupon.component';
import {ViewCouponComponent} from './view-coupon/view-coupon.component';
import {ListCouponComponent} from './list-coupon/list-coupon.component';
import {FormCouponComponent} from './form-coupon/form-coupon.component';
import {ComponentsModule} from '../components/components.module';
import {MemberLevelService} from "../../services/member-level.service";
import {CommodityService} from "../../services/commodity.service";
import {CouponProductService} from "../../services/coupon-product.service";
import {ProductService} from "../../services/product.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCouponComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCouponComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCouponComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCouponComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddCouponComponent,
        EditCouponComponent,
        ViewCouponComponent,
        ListCouponComponent,
        FormCouponComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [CouponService,MemberLevelService,CommodityService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CouponModule {
}
