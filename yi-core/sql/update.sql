
-- 更新订单项的 商品
UPDATE sale_order_item t1
LEFT JOIN product t2 ON t1.product_id = t2.id
LEFT JOIN commodity t3 ON t2.commodity_id = t3.id
SET t1.COMMODITY_ID = t3.id
WHERE
	t1.COMMODITY_ID IS NULL;

-- 修正地区数据
UPDATE region t1
LEFT JOIN area t2 ON t1.PROVINCE = t2.NAME
SET t1.AREA_ID = t2.id
WHERE
	t2.id IS NOT NULL;
	
	
-- 修正积分记录 非积分任务情况
UPDATE integral_record
SET TASK_NAME = '订单'
WHERE
	INTEGRAL_TASK_ID IS NULL;

-- 修正积分记录 积分任务情况
UPDATE integral_record t1
LEFT JOIN integral_task t2 ON t1.INTEGRAL_TASK_ID = t2.id
SET t1.TASK_NAME = t2.TASK_NAME
WHERE
	t1.INTEGRAL_TASK_ID IS NOT NULL;
	
-- 修正 积分记录 类型
UPDATE integral_record
SET TASK_TYPE = IF(
	INTEGRAL_TASK_ID IS NOT NULL,
	INTEGRAL_TASK_ID,
	4
)
WHERE
	TASK_TYPE IS NULL;
	
--  更新订单评价状态
UPDATE sale_order t1
SET t1.COMMENT_STATE =
IF (
	t1.STATE = 10,
	1,
IF (
	t1.STATE = 11,
	2,
	t1.COMMENT_STATE
)
);

-- 修复订单状态
UPDATE sale_order t1
SET t1.STATE = 4
WHERE
	t1.state = 10
OR t1.STATE = 11;

--  修复订单 供应商
UPDATE sale_order t1
LEFT JOIN sale_order_item t2 ON t1.id = t2.order_id
SET t1.SUPPLIER_ID = t2.SUPPLIER_ID
WHERE
	t1.SUPPLIER_ID IS NULL;
	
	
-- 修正 供应商账户
INSERT INTO supplier_account (
	SUPPLIER_ID,
	AMOUNT,
	BALANCE,
	FREEZE_AMOUNT,
	WITHDRAW_AMOUNT,
	CREATE_TIME
) SELECT
	t1.id,
	'0.00',
	'0.00',
	'0.00',
	'0.00',
	NOW()
FROM
	supplier t1
LEFT JOIN supplier_account t2 ON t1.id = t2.SUPPLIER_ID
WHERE
	t2.id IS NULL;

--  修复供应商账户数据
UPDATE supplier_account t2
LEFT JOIN (
	SELECT
		t1.SUPPLIER_ID,
		SUM(t1.PAY_AMOUNT) amount
	FROM
		sale_order t1
	WHERE
		t1.STATE IN (2, 3)
	GROUP BY
		t1.SUPPLIER_ID
) t3 ON t2.SUPPLIER_ID = t3.SUPPLIER_ID
SET t2.FREEZE_AMOUNT = t3.amount;

UPDATE supplier_account t2
LEFT JOIN (
	SELECT
		t1.SUPPLIER_ID,
		SUM(t1.PAY_AMOUNT) amount
	FROM
		sale_order t1
	WHERE
		t1.STATE IN (4)
	GROUP BY
		t1.SUPPLIER_ID
) t3 ON t2.SUPPLIER_ID = t3.SUPPLIER_ID
SET t2.BALANCE = t3.amount;

UPDATE supplier_account t1
SET t1.AMOUNT = IFNULL(t1.AMOUNT, 0.00),
 t1.BALANCE = IFNULL(t1.BALANCE, 0.00),
 t1.FREEZE_AMOUNT = IFNULL(t1.FREEZE_AMOUNT, 0.00);
 
--  修正库存供应商
UPDATE stock t1
LEFT JOIN commodity t2 ON t1.commodity_id = t2.id
SET t1.SUPPLIER_ID = t2.SUPPLIER_ID
WHERE
	t1.SUPPLIER_ID IS NULL
AND t2.id IS NOT NULL;

---  修复图片数据 测试环境
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8081', 'com');
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8082', 'com');
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8083', 'com');

update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8081', 'com');
update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8082', 'com');
update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8083', 'com');

update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8081', 'com');
update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8082', 'com');
update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8083', 'com');

update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8081', 'com');
update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8082', 'com');
update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8083', 'com');

update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8081', 'com');
update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8082', 'com');
update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8083', 'com');

update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8081', 'com');
update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8082', 'com');
update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8083', 'com');

update basic_info t1 set t1.LOGO_URL =REPLACE (t1.LOGO_URL , 'com:8081', 'com');
update basic_info t1 set t1.LOGO_URL  =REPLACE (t1.LOGO_URL , 'com:8082', 'com');
update basic_info t1 set t1.LOGO_URL =REPLACE (t1.LOGO_URL , 'com:8083', 'com');

update basic_info t1 set t1.CONTENT_PROFILE =REPLACE (t1.CONTENT_PROFILE , 'com:8081', 'com');
update basic_info t1 set t1.CONTENT_PROFILE  =REPLACE (t1.CONTENT_PROFILE , 'com:8082', 'com');
update basic_info t1 set t1.CONTENT_PROFILE =REPLACE (t1.CONTENT_PROFILE , 'com:8083', 'com');

update basic_rule t1 set t1.CONTENT =REPLACE (t1.CONTENT , 'com:8081', 'com');
update basic_rule t1 set t1.CONTENT  =REPLACE (t1.CONTENT , 'com:8082', 'com');
update basic_rule t1 set t1.CONTENT =REPLACE (t1.CONTENT , 'com:8083', 'com');

update user t1 set t1.AVATAR =REPLACE (t1.AVATAR , 'com:8081', 'com');
update user t1 set t1.AVATAR  =REPLACE (t1.AVATAR , 'com:8082', 'com');
update user t1 set t1.AVATAR =REPLACE (t1.AVATAR , 'com:8083', 'com');

update comment t1 set t1.IMG_PATH =REPLACE (t1.IMG_PATH , 'com:8081', 'com');
update comment t1 set t1.IMG_PATH  =REPLACE (t1.IMG_PATH , 'com:8082', 'com');
update comment t1 set t1.IMG_PATH =REPLACE (t1.IMG_PATH , 'com:8083', 'com');


---  修复图片数据 生产环境
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8181', 'com');
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8281', 'com');
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8381', 'com');

update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8181', 'com');
update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8281', 'com');
update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8381', 'com');

update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8181', 'com');
update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8281', 'com');
update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8381', 'com');

update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8181', 'com');
update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8281', 'com');
update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8381', 'com');

update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8181', 'com');
update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8281', 'com');
update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8381', 'com');

update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8181', 'com');
update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8281', 'com');
update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8381', 'com');

update basic_info t1 set t1.LOGO_URL =REPLACE (t1.LOGO_URL , 'com:8181', 'com');
update basic_info t1 set t1.LOGO_URL  =REPLACE (t1.LOGO_URL , 'com:8281', 'com');
update basic_info t1 set t1.LOGO_URL =REPLACE (t1.LOGO_URL , 'com:8381', 'com');

update basic_info t1 set t1.CONTENT_PROFILE =REPLACE (t1.CONTENT_PROFILE , 'com:8181', 'com');
update basic_info t1 set t1.CONTENT_PROFILE  =REPLACE (t1.CONTENT_PROFILE , 'com:8281', 'com');
update basic_info t1 set t1.CONTENT_PROFILE =REPLACE (t1.CONTENT_PROFILE , 'com:8381', 'com');

update basic_rule t1 set t1.CONTENT =REPLACE (t1.CONTENT , 'com:8181', 'com');
update basic_rule t1 set t1.CONTENT  =REPLACE (t1.CONTENT , 'com:8281', 'com');
update basic_rule t1 set t1.CONTENT =REPLACE (t1.CONTENT , 'com:8381', 'com');

update user t1 set t1.AVATAR =REPLACE (t1.AVATAR , 'com:8181', 'com');
update user t1 set t1.AVATAR  =REPLACE (t1.AVATAR , 'com:8281', 'com');
update user t1 set t1.AVATAR =REPLACE (t1.AVATAR , 'com:8381', 'com');

update comment t1 set t1.IMG_PATH =REPLACE (t1.IMG_PATH , 'com:8181', 'com');
update comment t1 set t1.IMG_PATH  =REPLACE (t1.IMG_PATH , 'com:8281', 'com');
update comment t1 set t1.IMG_PATH =REPLACE (t1.IMG_PATH , 'com:8381', 'com');

-- 订单数据优化
UPDATE sale_order
SET PAY_MODE = 0
WHERE
	PAY_MODE = 4;

UPDATE sale_order
SET PAY_INVALID_TIME = CLOSE_TIME
WHERE
	PAY_INVALID_TIME IS NULL;

UPDATE sale_order
SET CREATE_TIME = NOW()
WHERE
	CREATE_TIME IS NULL;

-- 修正支付渠道
UPDATE sale_order
SET PAYMENT_CHANNEL = 1
WHERE
	PAYMENT_CHANNEL IS NULL;
	
-- 修正 下单时间
UPDATE sale_order t
SET t.ORDER_TIME = t.CREATE_TIME
WHERE
	t.ORDER_TIME IS NULL;

-- 修正售后订单编号
UPDATE after_sale_order t1
LEFT JOIN sale_order t2 ON t1.SALE_ORDER_ID = t2.id
SET t1.ORDER_NO = t2.ORDER_NO
WHERE
	t2.ID IS NOT NULL;
	
	