/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.bo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 优惠券发放配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class CouponGrantConfigBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 发放配置ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 优惠券（coupon表ID）
	 */
	@NotNull
	private CouponBo coupon;
	/**
	 * 发放策略（1一次性发放，2分批发放）
	 */
	@Max(127)
	private Integer grantStrategy;
	/**
	 * 一次性发放节点（1购买，2收货，3评论，4超过15天）
	 */
	@Max(127)
	private Integer grantNode;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	@Max(127)
	private Integer state;
	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
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
	private Integer deleted;
	/**
	 * 删除时间
	 */

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;

	private Set<CommodityBo> commodities;

	private List<CouponGrantStepBo> couponGrantSteps;
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

	public CouponBo getCoupon() {
		return this.coupon;
	}

	public void setCoupon(CouponBo coupon) {
		this.coupon = coupon;
	}

	public Integer getGrantStrategy() {
		return this.grantStrategy;
	}

	public void setGrantStrategy(Integer grantStrategy) {
		this.grantStrategy = grantStrategy;
	}

	public Integer getGrantNode() {
		return this.grantNode;
	}

	public void setGrantNode(Integer grantNode) {
		this.grantNode = grantNode;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public Set<CommodityBo> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<CommodityBo> commodities) {
		this.commodities = commodities;
	}

	public List<CouponGrantStepBo> getCouponGrantSteps() {
		return couponGrantSteps;
	}

	public void setCouponGrantSteps(List<CouponGrantStepBo> couponGrantSteps) {
		this.couponGrantSteps = couponGrantSteps;
	}
}