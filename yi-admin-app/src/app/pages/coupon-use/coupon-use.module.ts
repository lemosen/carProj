


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {CouponUseService} from '../../services/coupon-use.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddCouponUseComponent} from './add-coupon-use/add-coupon-use.component';
import {EditCouponUseComponent} from './edit-coupon-use/edit-coupon-use.component';
import {ViewCouponUseComponent} from './view-coupon-use/view-coupon-use.component';
import {ListCouponUseComponent} from './list-coupon-use/list-coupon-use.component';
import {FormCouponUseComponent} from './form-coupon-use/form-coupon-use.component';
import {ComponentsModule} from '../components/components.module';

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCouponUseComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCouponUseComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCouponUseComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCouponUseComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddCouponUseComponent,
        EditCouponUseComponent,
        ViewCouponUseComponent,
        ListCouponUseComponent,
        FormCouponUseComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [CouponUseService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CouponUseModule {
}
