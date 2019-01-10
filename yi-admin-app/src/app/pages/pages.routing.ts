import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';

import {PagesComponent} from './pages.component';
import {ShippingTemplatesModule} from  './shipping-templates/shipping-templates.module';
import {SaleOrderModule} from  './sale-order/sale-order.module';
import {SupplierModule} from './supplier/supplier.module';

export const routes: Routes = [
    {
        path: '',
        component: PagesComponent,
        children: [
            {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
            {path: 'dashboard', loadChildren: 'app/pages/dashboard/dashboard.module#DashboardModule', data: {breadcrumb: '控制台'}},
            {path: 'system', loadChildren: null, data: {breadcrumb: '系统管理'}},
            {path: 'commodity', loadChildren: './commodity/commodity.module#CommodityModule', data: {breadcrumb: '商品管理&商品列表'}},
            {path: 'attribute-group', loadChildren: './attribute-group/attribute-group.module#AttributeGroupModule', data: {breadcrumb: '属性组'}},
            {path: 'attribute-value', loadChildren: './attribute-value/attribute-value.module#AttributeValueModule', data: {breadcrumb: '属性值'}},
            {path: 'attribute-name', loadChildren: './attribute-name/attribute-name.module#AttributeNameModule', data: {breadcrumb: '属性名'}},
            {path: 'comment', loadChildren: './comment/comment.module#CommentModule', data: {breadcrumb: '商品评价管理'}},
            {path: 'category', loadChildren: './category/category.module#CategoryModule', data: {breadcrumb: '商品分类管理'}},
            {path: 'article', loadChildren: './article/article.module#ArticleModule', data: {breadcrumb: '爱生活'}},
            {path: 'dept', loadChildren: './dept/dept.module#DeptModule', data: {breadcrumb: '部门管理'}},
            {path: 'user', loadChildren: './user/user.module#UserModule', data: {breadcrumb: '角色管理'}},
            {path: 'banner', loadChildren: './banner/banner.module#BannerModule', data: {breadcrumb: 'banner'}},
            {path: 'resc', loadChildren: './resc/resc.module#RescModule', data: {breadcrumb: '权限'}},

            {path: 'coupon', loadChildren: './coupon/coupon.module#CouponModule', data: {breadcrumb: '优惠券列表'}},
            {path: 'community', loadChildren: './community/community.module#CommunityModule', data: {breadcrumb: '小区列表'}},
            {path: 'refund-order', loadChildren: './refund-order/refund-order.module#RefundOrderModule', data: {breadcrumb: '退款处理'}},
            {path: 'return-order', loadChildren: './return-order/return-order.module#ReturnOrderModule', data: {breadcrumb: '退货处理'}},
            {path: 'order-setting', loadChildren: './order-setting/order-setting.module#OrderSettingModule', data: {breadcrumb: '订单设置'}},
            {path: 'member', loadChildren: './member/member.module#MemberModule', data: {breadcrumb: '会员列表'}},
            {path: 'withdraw', loadChildren: './withdraw/withdraw.module#WithdrawModule', data: {breadcrumb: '提现'}},

            {path: 'consume-record', loadChildren: './consume-record/consume-record.module#ConsumeRecordModule', data: {breadcrumb: '交易记录'}},

            {path: 'supplier', loadChildren: './supplier/supplier.module#SupplierModule', data: {breadcrumb: '供应商列表'}},
            {path: 'sale-order', loadChildren: './sale-order/sale-order.module#SaleOrderModule', data: {breadcrumb: '订单列表'}},
            {path: 'logistics', loadChildren: './logistics/logistics.module#LogisticsModule', data: {breadcrumb: '物流管理'}},
            {path: 'basic-config', loadChildren: './basic-config/basic-config.module#BasicConfigModule', data: {breadcrumb: '基础配置'}},
            {path: 'return-reason', loadChildren: './return-reason/return-reason.module#ReturnReasonModule', data: {breadcrumb: '退货原因设置'}},
            {path: 'express-template', loadChildren: './express-template/express-template.module#ExpressTemplateModule', data: {breadcrumb: '快递单模板设置'}},
            {path: 'freight-template', loadChildren: './freight-template/freight-template.module#FreightTemplateModule', data: {breadcrumb: '运费模板设置'}},

            {path: 'supplier-profile', loadChildren: './supplier-profile/supplier-profile.module#SupplierProfileModule', data: {breadcrumb: '供应商概况'}},
            {path: 'member-level', loadChildren: './member-level/member-level.module#MemberLevelModule', data: {breadcrumb: '会员等级'}},
            { path: 'daily-task', loadChildren: './daily-task/daily-task.module#DailyTaskModule', data: { breadcrumb: '积分管理' } },

            {path: 'region', loadChildren: './region/region.module#RegionModule', data: {breadcrumb: '地区列表'}},
            {path: 'platform-data', loadChildren: './platform-data/platform-data.module#PlatformDataModule', data: {breadcrumb: '平台数据'}},
            {path: 'platform_sale_stat', loadChildren: './platform_sale_stat/platform_sale_stat.module#PlatformSaleStatModule', data: {breadcrumb: '供应商销售统计'}},
            {path: 'supplier-withdraw', loadChildren: './supplier-withdraw/supplier-withdraw.module#SupplierWithdrawModule', data: {breadcrumb: '供应商提现申请'}},
            {path: 'supplier-record', loadChildren: './supplier-record/supplier-record.module#SupplierRecordModule', data: {breadcrumb: '供应商提现记录'}},
            {path: 'supplier-check-account', loadChildren: './supplier-check-account/supplier-check-account.module#SupplierCheckAccountModule', data: {breadcrumb: '供应商对账明细'}},

            {path: 'recommend', loadChildren: './recommend/recommend.module#RecommendModule', data: {breadcrumb: '推荐列表'}},
            // {path: 'theme', loadChildren: './theme/theme.module#ThemeModule', data: {breadcrumb: '文章管理'}},

            {path: 'storage-volume', loadChildren: './storage-volume/storage-volume.module#StorageVolumeModule', data: {breadcrumb: '储值券'}},


            {path: 'role', loadChildren: './role/role.module#RoleModule', data: {breadcrumb: '角色管理'}},


        ]
    }
];

        export const routing: ModuleWithProviders = RouterModule.forChild(routes);