package com.yi.core.basic;

/**
 * 基础相关枚举
 * 
 * @author xuyh
 *
 */
public enum BasicEnum {

	/** 启用 */
	STATE_ENABLE("启动", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 等待回执 */
	SEND_STATUS_WAIT_REPORT("等待回执", 1),
	/** 发送失败 */
	SEND_STATUS_FAILURE("发送失败", 2),
	/** 发送成功 */
	SEND_STATUS_SUCCESS("发送成功", 3),

	/** 系统消息 */
	MESSAGE_TYPE_SYSTEM("系统消息", 0),

	/** 积分任务类型 签到 */
	TASK_TYPE_SIGN("签到", 1),
	/** 积分任务类型 邀请好友 */
	TASK_TYPE_INVITE("邀请好友", 2),
	/** 积分任务类型 评论 */
	TASK_TYPE_COMMENT("评论", 3),
	/** 积分任务类型 订单 */
	TASK_TYPE_ORDER("订单", 4),

	/** 增加积分 */
	OPERATE_TYPE_ADD("新增", 0),
	/** 减少积分 */
	OPERATE_TYPE_SUBTRACT("减少", 1),

	;

	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private BasicEnum(String value, Integer code) {
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
