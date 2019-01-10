package com.yi.core.activity;

/**
 * 活动 相关枚举
 * 
 * @author xuyh
 *
 */
public enum ActivityEnum {

	/** 启用 */
	STATE_ENABLE("启用", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 优惠券类型-1优惠券 */
	COUPON_TYPE_COUPON("优惠券", 1),
	// /** 买送券 */
	// COUPON_TYPE_BUY_SEND("买送券", 2),
	/** 优惠券类型-2代金券 */
	COUPON_TYPE_VOUCHER("代金券", 2),
	/** 优惠券类型-3礼品券 */
	COUPON_TYPE_GIFT("礼品券", 3),

	/** 手工发放 */
	RECEIVE_MODE_MANUAL("手工发放", 1),
	/** 自助领取 */
	RECEIVE_MODE_SELF("自助领取", 2),
	/** 活动赠送 */
	RECEIVE_MODE_GIFT("活动赠送", 3),

	/** 有效期类型-时间段 */
	VALID_TYPE_PERIOD("时间段", 1),
	/** 有效期类型-固定天数 */
	VALID_TYPE_FIXED_DAY("固定天数", 2),

	// /** 开团成功 */
	// NATIONAL_GROUP_RECORD_STATE("开团成功", 2),
	// /** 等待开团 */
	// WAIT_GROUP_RECORD_STATE("等待参团", 1),

	/** 优惠券使用情况-未使用 */
	COUPON_USE_STATE_NO_USED("未使用", 1),
	/** 优惠券使用情况-已使用 */
	COUPON_USE_STATE_ALREADY_USED("已使用", 2),
	/** 优惠券使用情况-已失效 */
	COUPON_USE_STATE_INVALID("已失效", 3),
	/** 优惠券使用情况-已收回 */
	COUPON_USE_STATE_REGAIN("已收回", 4),

	/** 使用条件-无限制 */
	USE_CONDITION_TYPE_UNLIMITED("无限制", 0),
	/** 使用条件-满XX元可用 */
	USE_CONDITION_TYPE_FULL_MONEY("满XX元可用", 1),
	/** 使用条件-满XX件可用 */
	USE_CONDITION_TYPE_FULL_PIECE("满XX件可用", 2),

	/** 领取状态-未领取 */
	RECEIVE_STATE_NO("未领取", 0),
	/** 领取状态-已领取 */
	RECEIVE_STATE_YES("已领取", 1),

	/** 发放策略-1一次性发放 */
	GRANT_STRATEGY_ONCE("一次性发放 ", 1),
	/** 发放策略-2分批发放 */
	GRANT_STRATEGY_STEP("分步发放", 2),

	/** 发放节点-1下单 */
	GRANT_NODE_ORDER("下单", 1),
	/** 发放节点-2收货 */
	GRANT_NODE_RECEIPT("收货", 2),
	/** 发放节点-3评论 */
	GRANT_NODE_COMMENT("评论", 3),
	/** 发放节点-4超过15天 */
	GRANT_NODE_OVER_15_DAY("超过15天", 4),

	/** 奖励类型-1邀请 */
	REWARD_TYPE_INVITE("邀请", 1),
	/** 奖励类型-2评论 */
	REWARD_TYPE_COMMENT("评论", 2),
	/** 奖励类型-3连续签到 */
	REWARD_TYPE_SIGN("连续签到", 3),

	/** 奖品类型-1积分 */
	PRIZE_TYPE_INTEGRAL("积分", 1),
	/** 奖品类型-2商品 */
	PRIZE_TYPE_COMMODITY("商品", 2),
	/** 奖品类型-3优惠券 */
	PRIZE_TYPE_COUPON("优惠券", 3),

	;

	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private ActivityEnum(String value, Integer code) {
		this.code = code;
		this.value = value;
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
