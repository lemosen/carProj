/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.bo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
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
public class CommunityGroupRecordBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 小区拼团记录ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 小区拼团（community_group表ID）
	 */
	@NotNull
	private CommunityGroupBo communityGroup;
	/**
	 * 团编号
	 */
	@NotBlank
	@Length(max = 127)
	private String groupCode;
	/**
	 * 团长（member表ID）
	 */
	@NotNull
	private MemberBo member;
	/**
	 * 成团人数
	 */
	@NotNull
	@Max(9999999999L)
	private int groupPeople;
	/**
	 * 参团人数
	 */
	@NotNull
	@Max(9999999999L)
	private int joinPeople;
	/**
	 * 开团时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date openTime;
	/**
	 * 状态（1等待开团2开团成功3开团失败）
	 */
	@NotNull
	@Max(127)
	private int state;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	@Max(127)
	private int deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
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

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public CommunityGroupBo getCommunityGroup() {
		return communityGroup;
	}

	public void setCommunityGroup(CommunityGroupBo communityGroup) {
		this.communityGroup = communityGroup;
	}

	public MemberBo getMember() {
		return member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public int getGroupPeople() {
		return this.groupPeople;
	}

	public void setGroupPeople(int groupPeople) {
		this.groupPeople = groupPeople;
	}

	public int getJoinPeople() {
		return this.joinPeople;
	}

	public void setJoinPeople(int joinPeople) {
		this.joinPeople = joinPeople;
	}

	public Date getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}