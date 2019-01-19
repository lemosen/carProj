/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.bo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.member.domain.bo.MemberBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Deprecated
public class RebateGroupMemberBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 返现拼团成员ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 返现拼团记录（rebate_group_record表ID）
	 */
	@NotNull
	private RebateGroupRecordBo rebateGroupRecord;
	/**
	 * 成员（member表ID）
	 */
	@NotNull
	private MemberBo member;
	/**
	 * 收货人
	 */
	@Length(max = 16)
	private String consignee;
	/**
	 * 收货人电话
	 */
	@Length(max = 16)
	private String consigneePhone;

	/**
	 * 是否支付 0否 1是
	 */
	@NotNull
	@Max(127)
	private int Pay;

	/**
	 * 收货人地址
	 */
	@Length(max = 127)
	private String consigneeAddr;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	// columns END

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public RebateGroupRecordBo getRebateGroupRecord() {
		return rebateGroupRecord;
	}

	public void setRebateGroupRecord(RebateGroupRecordBo rebateGroupRecord) {
		this.rebateGroupRecord = rebateGroupRecord;
	}

	public MemberBo getMember() {
		return member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneePhone() {
		return this.consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeAddr() {
		return this.consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getPay() {
		return Pay;
	}

	public void setPay(int pay) {
		Pay = pay;
	}
}