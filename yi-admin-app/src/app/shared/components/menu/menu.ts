import { Menu } from './menu.model';

export const verticalMenuItems = [ 
    new Menu (1, '控制台', '/pages/dashboard', null, 'tachometer', null, false, 0),

    new Menu (10, '首页', null, null, 'wrench', null, true, 0),
    new Menu (101, '平台数据', 'pages/platform-data', null, 'pencil-square-o', null, false, 10),


    new Menu (11, '商品管理', null, null, 'wrench', null, true, 0),
    new Menu (110, '商品分类', '/pages/category', null, 'pencil-square-o', null, false, 11),
    new Menu (112, '商品列表', '/pages/commodity', null, 'pencil-square-o', null, false, 11),
    new Menu (113, 'banner', '/pages/banner', null, 'pencil-square-o', null, false, 11),
    /*new Menu (116, '代审核商品', '/pages/commodity', null, 'pencil-square-o', null, false, 11),*/
    new Menu (117, '商品评价管理', '/pages/comment', null, 'pencil-square-o', null, false, 11),


    // new Menu (15, '用户管理', '/pages/system/user', null, 'check-square-o', null, false, 14),
    // new Menu (16, '部门管理', '/pages/system/dept', null, 'pencil-square-o', null, false, 14),
    // new Menu (17, '配置管理', '/pages/system/system-config', null, 'pencil-square-o', null, false, 14),
    new Menu (12, '会员管理', null, null, 'wrench', null, true, 0),
    new Menu (121, '会员等级', '/pages/member-level', null, 'pencil-square-o', null, false, 12),
    new Menu (122, '会员列表', 'pages/member', null, 'pencil-square-o', null, false, 12),
    new Menu (123, '积分管理', 'pages/daily-task', null, 'pencil-square-o', null, false, 12),
/*    new Menu (124, '提现管理', "pages/withdraw", null, 'pencil-square-o', null, false, 12),*/

    new Menu (18, '内容管理', null, null, 'wrench', null, true, 0),
    new Menu (181, '爱生活', "pages/article", null, 'pencil-square-o', null, false, 18),
    // new Menu (182, '主题列表', "pages/theme", null, 'pencil-square-o', null, false, 18),

    new Menu (19, '营销管理', null, null, 'wrench', null, true, 0),
    new Menu (191, '优惠券列表', "pages/coupon", null, 'pencil-square-o', null, false, 19),
    new Menu (192, '储值券列表', "pages/storage-volume", null, 'pencil-square-o', null, false, 19),
    // new Menu (193, '全国拼团', null, null, 'pencil-square-o', null, false, 19),


    new Menu (20, '订单管理', null, null, 'wrench', null, true, 0),
    new Menu (201, '订单列表', "pages/sale-order", null, 'pencil-square-o', null, false, 20),
    new Menu (202, '退款处理', "pages/refund-order", null, 'pencil-square-o', null, false, 20),
    new Menu (203, '退货处理', "pages/return-order", null, 'pencil-square-o', null, false, 20),
    new Menu (204, '订单设置', "pages/order-setting", null, 'pencil-square-o', null, false, 20),
    new Menu (205, '退货原因设置', "pages/return-reason", null, 'pencil-square-o', null, false, 20),
    new Menu (206, '快递单模板设置', "pages/express-template", null, 'pencil-square-o', null, false, 20),
    new Menu (207, '运费模板设置', "pages/freight-template", null, 'pencil-square-o', null, false, 20),
    new Menu (208, '交易记录', "pages/consume-record", null, 'pencil-square-o', null, false, 20),
    // new Menu (209, '物流管理', "pages/logistics", null, 'pencil-square-o', null, false, 20),

    new Menu (21, '供应商管理', null, null, 'wrench', null, true, 0),
    /*new Menu (211, '供应商概况', "pages/supplier-profile" ,null, 'pencil-square-o', null, false, 21),*/
    new Menu (212, '供应商列表', "pages/supplier", null, 'pencil-square-o', null, false, 21),
    /*  new Menu (212, '供应商销售列表', null, null, 'pencil-square-o', null, false, 21),
     new Menu (212, '供应商结算列表', null, null, 'pencil-square-o', null, false, 21),
 */
    new Menu (22, '系统管理', null, null, 'wrench', null, true, 0),
    new Menu (222, '成员管理', "/pages/user", null, 'pencil-square-o', null, false, 22),
/*    new Menu (223, '成员管理', null, null, 'pencil-square-o', null, false, 22),
      new Menu (225, '用户管理', null, null, 'check-square-o', null, false, 22),*/
    new Menu (223, '角色管理', "/pages/role", null, 'pencil-square-o', null, false, 22),
    new Menu (226, '部门管理', "/pages/dept", null, 'pencil-square-o', null, false, 22),
/*    new Menu (227, '配置管理', null, null, 'pencil-square-o', null, false, 22),
    new Menu (228, '基础配置', 'pages/basic-config', null, 'pencil-square-o', null, false, 22),*/

    new Menu (23, '财务', null, null, 'wrench', null, true, 0),
    new Menu (231, '供应商销售统计', "/pages/platform_sale_stat", null, 'pencil-square-o', null, false, 23),
    // new Menu (232, '平台销售统计', "/pages/platform_sale_stat", null, 'pencil-square-o', null, false, 23),
    new Menu (233, '供应商提现申请', "/pages/supplier-withdraw", null, 'pencil-square-o', null, false, 23),
    new Menu (234, '供应商提现记录', "/pages/supplier-record", null, 'pencil-square-o', null, false, 23),
    new Menu (235, '供应商对账明细', "/pages/supplier-check-account", null, 'pencil-square-o', null, false, 23),



    /*new Menu (24, '小区管理员分成', null, null, 'wrench', null, true, 0),
    new Menu (241, '小区管理员设置', "" ,null, 'pencil-square-o', null, false, 24),
    new Menu (242, '小区提成设置', "", null, 'pencil-square-o', null, false, 24),
    new Menu (243, '小区业绩管理', "" ,null, 'pencil-square-o', null, false, 24),
    new Menu (244, '小区提成列表', "", null, 'pencil-square-o', null, false, 24),*/

    new Menu (25, '地区管理', null, null, 'wrench', null, true, 0),
    new Menu (251, '地区列表', "pages/region" ,null, 'pencil-square-o', null, false, 25),
    new Menu (252, '小区列表', "/pages/community", null, 'pencil-square-o', null, false, 25),

    new Menu (26, '推荐系统', null, null, 'wrench', null, true, 0),
    new Menu (261, '推荐位设置', "pages/recommend" ,null, 'pencil-square-o', null, false, 26),
    /*new Menu (262, '推荐位添加', "" ,null, 'pencil-square-o', null, false, 26),
    new Menu (263, '推荐位列表', "" ,null, 'pencil-square-o', null, false, 26),
*/

]

export const horizontalMenuItems = [ 
    new Menu (1, 'Dashboard', '/pages/dashboard', null, 'tachometer', null, false, 0),
    new Menu (2, 'Membership', '/pages/membership', null, 'users', null, false, 0), 
    new Menu (3, 'UI Features', null, null, 'laptop', null, true, 0),   
    new Menu (4, 'Buttons', '/pages/ui/buttons', null, 'keyboard-o', null, false, 3),  
    new Menu (5, 'Cards', '/pages/ui/cards', null, 'address-card-o', null, false, 3), 
    new Menu (6, 'Components', '/pages/ui/components', null, 'creative-commons', null, false, 3),
    new Menu (7, 'Icons', '/pages/ui/icons', null, 'font-awesome', null, false, 3), 
    new Menu (8, 'List Group', '/pages/ui/list-group', null, 'th-list', null, false, 3), 
    new Menu (9, 'Media Objects', '/pages/ui/media-objects', null, 'object-group', null, false, 3), 
    new Menu (10, 'Tabs & Accordions', '/pages/ui/tabs-accordions', null, 'server', null, false, 3),
    new Menu (11, 'Typography', '/pages/ui/typography', null, 'font', null, false, 3),
    new Menu (31, 'Tools', null, null, 'wrench', null, true, 3),
    new Menu (32, 'Drag & Drop', '/pages/tools/drag-drop', null, 'hand-paper-o', null, false, 31), 
    new Menu (33, 'Resizable', '/pages/tools/resizable', null, 'expand', null, false, 31), 
    new Menu (34, 'Toaster', '/pages/tools/toaster', null, 'bell-o', null, false, 31), 
    new Menu (20, 'Form Elements', null, null, 'pencil-square-o', null, true, 0), 
    new Menu (21, 'Form Controls', '/pages/form-elements/controls', null, 'check-square-o', null, false, 20),
    new Menu (22, 'Form Layouts', '/pages/form-elements/layouts', null, 'th-large', null, false, 20),
    new Menu (23, 'Form Validations', '/pages/form-elements/validations', null, 'exclamation-triangle', null, false, 20),
    new Menu (24, 'Form Wizard', '/pages/form-elements/wizard', null, 'magic', null, false, 20),
    new Menu (25, 'Editor', '/pages/form-elements/editor.css', null, 'pencil', null, false, 20),
    new Menu (26, 'Tables', null, null, 'table', null, true, 20),
    new Menu (27, 'Basic Tables', '/pages/tables/basic-tables', null, 'th', null, false, 26), 
    new Menu (28, 'Dynamic Tables', null, null, 'th-large', null, true, 26), 
    new Menu (29, 'Smart DataTable', '/pages/tables/dynamic-tables/smart', null, 'caret-right', null, false, 28),
    new Menu (30, 'NGX DataTable', '/pages/tables/dynamic-tables/ngx', null, 'caret-right', null, false, 28), 
    new Menu (40, 'Pages', null, null, 'file-text-o', null, true, 0),
    new Menu (15, 'Dynamic Menu', '/pages/dynamic-menu', null, 'list-ul', null, false, 40), 
    new Menu (43, 'Login', '/login', null, 'sign-in', null, false, 40),    
    new Menu (44, 'Register', '/register', null, 'registered', null, false, 40),
    new Menu (45, 'Blank', '/pages/blank', null, 'file-o', null, false, 40),
    new Menu (46, 'Error', '/pagenotfound', null, 'exclamation-circle', null, false, 40),
    new Menu (50, 'Calendar', '/pages/calendar', null, 'calendar', null, false, 40),
    new Menu (16, 'Mailbox', '/pages/mailbox', null, 'envelope-o', null, false, 40), 
    new Menu (200, 'External Link', null, 'http://themeseason.com', 'external-link', '_blank', false, 40),
    new Menu (66, 'Maps', null, null, 'globe', null, true, 0),
    new Menu (67, 'Google Maps', '/pages/maps/googlemaps', null, 'map-marker', null, false, 66),
    new Menu (68, 'Leaflet Maps', '/pages/maps/leafletmaps', null, 'map-o', null, false, 66),
    new Menu (70, 'Charts', null, null, 'area-chart', null, true, 0),
    new Menu (71, 'Bar Charts', '/pages/charts/bar', null, 'bar-chart', null, false, 70),
    new Menu (72, 'Pie Charts', '/pages/charts/pie', null, 'pie-chart', null, false, 70),
    new Menu (73, 'Line Charts', '/pages/charts/line', null, 'line-chart', null, false, 70),
    new Menu (74, 'Bubble Charts', '/pages/charts/bubble', null, 'comment-o', null, false, 70)
]