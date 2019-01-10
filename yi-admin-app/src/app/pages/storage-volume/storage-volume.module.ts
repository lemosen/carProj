


import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {CouponService} from '../../services/coupon.service';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {AddStorageVolumeComponent} from './add-storage-volume/add-storage-volume.component';
import {EditStorageVolumeComponent} from './edit-storage-volume/edit-storage-volume.component';
import {ViewStorageVolumeComponent} from './view-coupon/view-storage-volume.component';
import {ListStorageVolumeComponent} from './list-storage-volume/list-storage-volume.component';
import {FormStorageVolumeComponent} from './form-storage-volume/form-storage-volume.component';
import {ComponentsModule} from '../components/components.module';
import {MemberLevelService} from "../../services/member-level.service";
import {CommodityService} from "../../services/commodity.service";
import {CouponProductService} from "../../services/coupon-product.service";
import {ProductService} from "../../services/product.service";

export const routes = [
    {path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListStorageVolumeComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddStorageVolumeComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditStorageVolumeComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewStorageVolumeComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
    imports: [
        SharedModule,
        ComponentsModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        AddStorageVolumeComponent,
        EditStorageVolumeComponent,
        ViewStorageVolumeComponent,
        ListStorageVolumeComponent,
        FormStorageVolumeComponent,
    ],
    exports: [
        RouterModule
    ],
    entryComponents: [],
    providers: [CouponService,MemberLevelService,CommodityService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StorageVolumeModule {
}
