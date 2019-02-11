/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.domain.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.yi.core.auth.domain.simple.UserSimple;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yihz.common.json.serializer.JsonDateSerializer;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.auth.domain.vo.UserVo;
import com.yi.core.promotion.domain.simple.GroupBuyActivityMemberSimple;
import com.yi.core.promotion.domain.simple.GroupBuyActivityProductSimple;
import com.yi.core.promotion.domain.simple.GroupBuyActivityRuleSimple;
import com.yi.core.promotion.domain.simple.GroupBuyActivityTimeSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class GroupBuyActivityVo extends VoDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// columns START
	/**
	 *
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * 编码
	 */
	@NotBlank
	@Length(max = 32)
	private String guid;
	/**
	 * 团购活动名称
	 */
	@NotBlank
	@Length(max = 128)
	private String activityName;
	/**
	 * 优先级
	 */
	@NotNull
	@Max(127)
	private int priority;
	/**
	 * 团购活动类型
	 */
	@NotNull
	@Max(127)
	private int type;
	/**
	 * 优惠方式类型
	 */
	@Max(127)
	private int promotionType;
	/**
	 * 团购活动主办方
	 */
	@Length(max = 32)
	private String sponsor;
	/**
	 * 团购活动封面
	 */
	@NotBlank
	@Length(max = 255)
	private String coverUrl;
	/**
	 * 是否包邮
	 */
	@NotNull
	private Integer hasPost;
	/**
	 * 是否支持优惠券抵扣
	 */
	@NotNull
	private Integer hasCoupon;
	/**
	 * 审核状态 :0为未审核,1为审核
	 */
	@NotNull
	private Integer audited;
	/**
	 * 发布状态:0为未发布,1为以发布
	 */
	@NotNull
	private Integer published;
	/**
	 * 库存状态
	 */
	@NotNull
	@Max(127)
	private int stockState;
	/**
	 * 删除状态
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
	/**
	 * 审核人
	 */
	private UserSimple auditUser;
	/**
	 * 审核时间
	 */
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date auditDate;
	/**
	 * 发布人
	 */
	private UserSimple publishUser;
	/**
	 * 发布时间
	 */
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date publishDate;
	/**
	 * 更新人(更新，终结，删除)
	 */
	private UserSimple updateUser;
	/**
	 * 更新时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date updateDate;
	/**
	 * 创建人
	 */
	@NotNull
	private UserSimple createUser;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 终结状态 0:未终结，1：已终结（如果活动有多个时间段的，则必须最后一个时间段结束后，才能变为已终结状态）
	 */
	@NotNull
	private Integer finished;

	// columns END

	private Set<GroupBuyActivityProductSimple> groupBuyActivityProducts = new HashSet<>(0);

	private Set<GroupBuyActivityRuleSimple> groupBuyActivityRules = new HashSet<>(0);

	private Set<GroupBuyActivityMemberSimple> groupBuyActivityMembers = new HashSet<>(0);

	private GroupBuyActivityMemberSimple groupBuyActivityMember;

	private Set<GroupBuyActivityTimeSimple> groupBuyActivityTimes = new HashSet<>(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(int promotionType) {
		this.promotionType = promotionType;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public Integer getHasPost() {
		return hasPost;
	}

	public void setHasPost(Integer hasPost) {
		this.hasPost = hasPost;
	}

	public Integer getHasCoupon() {
		return hasCoupon;
	}

	public void setHasCoupon(Integer hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	public Integer getAudited() {
		return audited;
	}

	public void setAudited(Integer audited) {
		this.audited = audited;
	}

	public Integer getPublished() {
		return published;
	}

	public void setPublished(Integer published) {
		this.published = published;
	}

	public int getStockState() {
		return stockState;
	}

	public void setStockState(int stockState) {
		this.stockState = stockState;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserSimple getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(UserSimple auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public UserSimple getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(UserSimple publishUser) {
		this.publishUser = publishUser;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public UserSimple getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(UserSimple updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public UserSimple getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserSimple createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getFinished() {
		return finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}

	public Set<GroupBuyActivityProductSimple> getGroupBuyActivityProducts() {
		return groupBuyActivityProducts;
	}

	public void setGroupBuyActivityProducts(Set<GroupBuyActivityProductSimple> groupBuyActivityProducts) {
		this.groupBuyActivityProducts = groupBuyActivityProducts;
	}

	public Set<GroupBuyActivityRuleSimple> getGroupBuyActivityRules() {
		return groupBuyActivityRules;
	}

	public void setGroupBuyActivityRules(Set<GroupBuyActivityRuleSimple> groupBuyActivityRules) {
		this.groupBuyActivityRules = groupBuyActivityRules;
	}

	public Set<GroupBuyActivityMemberSimple> getGroupBuyActivityMembers() {
		return groupBuyActivityMembers;
	}

	public void setGroupBuyActivityMembers(Set<GroupBuyActivityMemberSimple> groupBuyActivityMembers) {
		this.groupBuyActivityMembers = groupBuyActivityMembers;
	}

	public GroupBuyActivityMemberSimple getGroupBuyActivityMember() {
		return groupBuyActivityMember;
	}

	public void setGroupBuyActivityMember(GroupBuyActivityMemberSimple groupBuyActivityMember) {
		if(groupBuyActivityMembers != null){
			groupBuyActivityMembers.forEach(e->{
				this.groupBuyActivityMember = e;
			});
		}else{
			this.groupBuyActivityMember = groupBuyActivityMember;
		}
	}

	public Set<GroupBuyActivityTimeSimple> getGroupBuyActivityTimes() {
		return groupBuyActivityTimes;
	}

	public void setGroupBuyActivityTimes(Set<GroupBuyActivityTimeSimple> groupBuyActivityTimes) {
		this.groupBuyActivityTimes = groupBuyActivityTimes;
	}

}