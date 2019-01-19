package com.yi.core.order;

/**
 * 订单相关枚举
 * 
 * @author xuyh
 *
 */
public enum OrderEnum {

	/** 启用 */
	STATE_ENABLE("启动", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 订单类型-普通订单 */
	ORDER_TYPE_ORDINARY("普通订单", 0),
	/** 订单类型-礼物订单 */
	ORDER_TYPE_GIFT("礼物订单", 1),
	/** 订单类型-团购订单 */
	ORDER_TYPE_GROUP("团购订单", 2),

	/** 订单状态-待付款 */
	ORDER_STATE_WAIT_PAY("待付款", 1),
	/** 订单状态-已付款待发货 */
	ORDER_STATE_WAIT_DELIVERY("待发货", 2),
	/** 订单状态-已发货待收货 */
	ORDER_STATE_WAIT_RECEIPT("待收货", 3),
	/** 订单状态-已收货已完成 */
	ORDER_STATE_ALREADY_FINISH("已完成", 4),
	/** 订单状态-已关闭已取消 */
	ORDER_STATE_ALREADY_CLOSE("已关闭", 5),

	/** 评价状态-待评价 */
	COMMENT_STATE_WAIT("待评价", 1),
	/** 评价状态-已评价 */
	COMMENT_STATE_ALREADY("已评价", 2),

	/** 售后状态-可申请 */
	AFTER_SALE_STATE_CAN_APPLY("可申请", 1),
	/** 售后状态-申请中 */
	AFTER_SALE_STATE_APPLY("申请中", 2),
	/** 售后状态-已申请 */
	AFTER_SALE_STATE_COMPLETED("已申请", 3),
	/** 售后状态-已过期 */
	AFTER_SALE_STATE_EXPIRE("已过期", 4),

	/** 礼物单类型-送礼单 */
	GIFT_ORDER_TYPE_SEND("送礼单", 1),
	/** 礼物单类型-收礼单 */
	GIFT_ORDER_TYPE_RECEIPT("收礼单", 2),

	/** 团购订单类型-1开团单 */
	GROUP_ORDER_TYPE_REBATE("开团单", 1),
	/** 团购订单类型-2参团单 */
	GROUP_ORDER_TYPE_SECKILL("参团单", 2),

	/** 全国拼团 */
	GROUP_TYPE_NATIONAL("全国拼团", 1),
	/** 小区拼团 */
	GROUP_TYPE_COMMUNITY("小区拼团", 2),
	/** 返现拼团 */
	GROUP_TYPE_REBATE("返现拼团", 3),
	/** 秒杀活动 */
	GROUP_TYPE_SECKILL("秒杀活动", 4),

	/** 已支付 */
	ALREADY_PAID("已支付", 1),
	/** 等待开团 */
	WAIT_FOR_GROUP("等待开团", 1),

	ALREADY_GROUP("已开团", 2),

	/** 下单减库存 */
	STOCK_SET_ORDER("下单减库存", 1),
	/** 支付减库存 */
	STOCK_SET_PAYMENT("支付减库存", 2),

	NATIONAL_GROUP_RECORD_WAIT_FOR("等待开团", 1),

	NATIONAL_GROUP_RECORD_STATE("开团成功", 2),

	// 运费模板相关
	/** 发货时间 12H内 */
	DELIVERY_TIME_12HOUR("12小时", 12),
	/** 发货时间 24H内 */
	DELIVERY_TIME_24HOUR("24小时", 24),
	/** 发货时间 1天内 */
	DELIVERY_TIME_1DAY("1天", 1),
	/** 发货时间 3天内 */
	DELIVERY_TIME_3DAY("3天", 3),
	/** 发货时间 5天内 */
	DELIVERY_TIME_5DAY("5天", 5),

	/** 运费类型-自定义运费 */
	FREIGHT_TYPE_CUSTOM("自定义运费", 1),
	/** 运费类型-卖家承担运费 */
	FREIGHT_TYPE_SELLER("卖家承担运费", 2),

	/** 计价方式-按件数 */
	CHARGE_MODE_PIECE("按件数", 1),
	/** 计价方式-按重量 */
	CHARGE_MODE_WEIGHT("按重量", 2),
	/** 计价方式-按体积 */
	CHARGE_MODE_VOLUME("按体积", 3),

	/** 运送方式（1快递，2EMS，3平邮） */
	DELIVERY_MODE_EXPRESS("按重量", 1),
	/** 运送方式（1快递，2EMS，3平邮） */
	DELIVERY_MODE_EMS("EMS", 2),
	/** 运送方式（1快递，2EMS，3平邮） */
	DELIVERY_MODE("平邮", 2),

	/** 指定条件包邮 未选中 */
	FREE_CONDITION_UNCHECKED("未选中", 0),
	/** 指定条件包邮 选中 */
	FREE_CONDITION_CHECKED("选中", 1),

	/** 默认 */
	DEFAULT_YES("默认", 1),
	/** 非默认 */
	DEFAULT_NO("非默认", 0),

	/** 包邮条件-件数 */
	FREE_TYPE_PIECE("件数", 1),
	/** 包邮条件-金额 */
	FREE_TYPE_AMOUNT("金额", 2),
	/** 包邮条件-件数+金额 */
	FREE_TYPE_PIECE_AND_AMOUNT("件数+金额", 3),

	COUPON_TYPE_GIVE("买送券", 2),

	/** 创建订单 */
	ORDER_LOG_STATE_CREATE_ORDER("创建订单", 1),
	/** 支付成功 */
	ORDER_LOG_STATE_PAY_SUCCESS("支付成功", 2),
	/** 开始配送 */
	ORDER_LOG_STATE_DISTRIBUTION("开始配送", 3),
	/** 确认收货 */
	ORDER_LOG_STATE_CONFIRM_RECEIPT("确认收货", 4),
	/** 关闭订单 */
	ORDER_LOG_STATE_CLOSE_ORDER("关闭订单", 5),
	/** 订单退款 */
	ORDER_LOG_STATE_REFUND("订单退款", 6),

	/** 支付宝 */
	PAY_MODE_ALIPAY("支付宝", 1),
	/** 微信 */
	PAY_MODE_WECHAT_PAY("微信", 2),
	/** 银联 */
	PAY_MODE_UNIONPAY("银联", 3),

	/** 供应商阅读状态 未读 */
	READ_TYPE_NO("未读", 0),
	/** 供应商阅读状态 已读 */
	READ_TYPE_YES("已读", 1),

	/** 时间单位-天 */
	TIME_UNIT_DAY("天", 1),
	/** 时间单位-小时 */
	TIME_UNIT_HOUR("小时", 2),
	/** 时间单位-分钟 */
	TIME_UNIT_MINUTE("分钟", 3),

	/** 订单设置类型 -秒杀订单未付款 */
	ORDER_SET_TYPE_SECKILL("秒杀", 1),
	/** 订单设置类型 -正常订单未付款 */
	ORDER_SET_TYPE_NORMAL("正常", 2),
	/** 订单设置类型 -未收货 */
	ORDER_SET_TYPE_RECEIPT("收货", 3),
	/** 订单设置类型 -交易 */
	ORDER_SET_TYPE_TRADE("交易", 4),
	/** 订单设置类型 -未评论 */
	ORDER_SET_TYPE_COMMENT("评论", 5),

	/** 待处理 */
	RETURN_STATE_WAIT_PROCESS("待处理", 1),
	/** 待处理 */
	RETURN_STATE_WAIT_ALREADY("已处理", 2),
	/** 确认退 */
	RETURN_STATE_TO_BE_CONFIRM("确认退", 1),
	/** 拒绝退 */
	RETURN_STATE_TO_BE_TREATED("拒绝退", 2),
	/** 已完成 */
	RETURN_STATE_COMPLETE("已完成", 3),

	/** 退款 */
	RETURN_STATE_REFUND("退款", 4),

	RETURN_PROCESS_TYPE_REFUND("确认退款", 4),

	RETURN_PROCESS_TYPE_REFUSE("拒绝退款", 5),

	RETURN_STATE_STAY("待收货", 2),

	RETURN_STATE_DELIVERY("发货", 3),

	RETURN_STATE_CONFIRM("确认收货", 3),

	RETURN_STATE_ALREADY("已完成", 10),

	RETURN_STATE_CANCLE("已取消", 5),

	/** 退货申请状态-待处理 */
	RETURN_APPLY_STATE_WAIT_PROCESS("待处理", 1),
	/** 退货申请状态-处理中 */
	RETURN_APPLY_STATE_IN_PROCESS("处理中", 2),
	/** 退货申请状态-已处理 */
	RETURN_APPLY_STATE_ALREADY_PROCESS("已处理", 3),

	/** 退款申请状态-待处理 */
	REFUND_APPLY_STATE_WAIT_PROCESS("待处理", 1),
	/** 退款申请状态-处理中 */
	REFUND_APPLY_STATE_IN_PROCESS("处理中", 2),
	/** 退款申请状态-已处理 */
	REFUND_APPLY_STATE_ALREADY_PROCESS("已处理", 3),

	/** 处理类型-待处理 */
	PROCESS_TYPE_WAIT("待处理", 0),
	/** 处理类型-确认 */
	PROCESS_TYPE_CONFIRM("确认", 1),
	/** 处理类型- 拒绝 */
	PROCESS_TYPE_REFUSE("拒绝", 2),

	/** 退货状态-待审核 */
	RETURN_STATE_WAIT_AUDIT("待审核", 1),
	/** 退货状态-审核通过，待退货 */
	RETURN_STATE_WAIT_RETURN("待退货", 2),
	/** 退货状态-七日上传运单号，待退款 */
	RETURN_STATE_WAIT_REFUND("待退款", 3),
	/** 退货状态-已完成 */
	RETURN_STATE_FINISH("已完成", 4),

	// ------------------------------------------------

	/** 售后类型-退款 */
	AFTER_SALE_TYPE_REFUND("退款", 1),
	/** 售后类型-退货退款 */
	AFTER_SALE_TYPE_RETURN("退货退款", 2),
	/** 售后类型-换货 */
	AFTER_SALE_TYPE_EXCHANGE("换货", 3),

	/** 申请状态-审核中 */
	APPLY_STATE_AUDIT("审核中", 1),
	/** 申请状态-处理中 */
	APPLY_STATE_PROCESS("处理中", 2),
	/** 申请状态-已完成 */
	APPLY_STATE_FINISH("已完成", 3),

	/** 处理状态-已申请待审核 */
	PROCESS_STATE_WAIT_AUDIT("待审核", 1),
	/** 处理状态-确认退货待收货 */
	PROCESS_STATE_WAIT_RECEIPT("待收货", 2),
	/** 处理状态-确认收货待退款 */
	PROCESS_STATE_WAIT_REFUND("待退款", 3),
	/** 处理状态-已退款已完成 */
	PROCESS_STATE_FINISH("已完成", 4),
	/** 处理状态-拒绝退货 */
	PROCESS_STATE_REFUSE_RETURN("拒绝退货", 5),
	/** 处理状态-拒绝退款 */
	PROCESS_STATE_REFUSE_REFUND("拒绝退款", 6),

	/** 退款支付状态-待回执 */
	REFUND_PAY_STATE_WAIT_RECEIPT("待回执", 1),
	/** 退款支付状态-已回执 */
	REFUND_PAY_STATE_ALREADY_RECEIPT("已回执", 2),

	/** 退款方式（1退回原支付渠道） */
	REFUND_MODE_ORIGINAL_CHANNEL("原渠道", 1),

	/** 订单来源 -服务号 */
	ORDER_SOURCE_SP("服务号", 1),
	/** 订单来源 -小程序 */
	ORDER_SOURCE_MP("小程序", 2),
	/** 订单来源 -APP */
	ORDER_SOURCE_APP("APP", 3),

	/** 支付渠道 -服务号 */
	PAYMENT_CHANNEL_SP("服务号", 1),
	/** 支付渠道-小程序 */
	PAYMENT_CHANNEL_MP("小程序", 2),
	/** 支付渠道 -APP-微信 */
	PAYMENT_CHANNEL_APP_WECHAT("APP-微信", 3),
	/** 支付渠道 -APP-支付宝 */
	PAYMENT_CHANNEL_APP_ALIPAY("APP-支付宝", 4),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private OrderEnum(String value, Integer code) {
		this.value = value;
		this.code = code;
	}

	// get set 方法
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
