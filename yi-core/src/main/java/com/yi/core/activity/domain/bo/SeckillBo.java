/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.commodity.domain.bo.ProductBo;
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
public class SeckillBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 秒杀活动ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 活动标签
	 */
	@NotBlank
	@Length(max = 255)
	private String label;
	/**
	 * 开始时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date startTime;
	/**
	 * 结束时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date endTime;
	/**
	 * 活动封面
	 */
	@Length(max = 255)
	private String activityCover;
	/**
	 * 分享标题
	 */
	@Length(max = 255)
	private String shareTitle;
	/**
	 * 商品（product表ID）
	 */
	@NotNull
	private ProductBo product;
	/**
	 * 活动库存
	 */
	@NotNull
	@Max(9999999999L)
	private int activityStock;
	/**
	 * 秒杀价
	 */
	@NotNull
	private BigDecimal seckillPrice;
	/**
	 * 限购数量
	 */
	@NotNull
	@Max(9999999999L)
	private int limitQuantity;
	/**
	 * 支付时限（单位分钟），XX分钟内不支付，自动释放库存
	 */
	@NotNull
	@Max(9999999999L)
	private int limitPayTime;
	/**
	 * 奖励类型（1积分2优惠券）
	 */
	@NotNull
	@Max(127)
	private int rewardType;
	/**
	 * 奖励积分
	 */
	@Max(9999999999L)
	private int rewardIntegral;
	/**
	 * 优惠券（coupon表ID）
	 */
	private CouponBo coupon;
	/**
	 * 运费设置（1卖家包邮2买家承担运费）
	 */
	@NotNull
	@Max(127)
	private int freightSet;
	/**
	 * 运费
	 */
	@NotNull
	private BigDecimal freight;
	/**
	 * 状态（0启用1禁用）
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

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getActivityCover() {
		return this.activityCover;
	}

	public void setActivityCover(String activityCover) {
		this.activityCover = activityCover;
	}

	public String getShareTitle() {
		return this.shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public ProductBo getProduct() {
		return product;
	}

	public void setProduct(ProductBo product) {
		this.product = product;
	}

	public CouponBo getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponBo coupon) {
		this.coupon = coupon;
	}

	public int getActivityStock() {
		return this.activityStock;
	}

	public void setActivityStock(int activityStock) {
		this.activityStock = activityStock;
	}

	public BigDecimal getSeckillPrice() {
		return this.seckillPrice;
	}

	public void setSeckillPrice(BigDecimal seckillPrice) {
		this.seckillPrice = seckillPrice;
	}

	public int getLimitQuantity() {
		return this.limitQuantity;
	}

	public void setLimitQuantity(int limitQuantity) {
		this.limitQuantity = limitQuantity;
	}

	public int getLimitPayTime() {
		return this.limitPayTime;
	}

	public void setLimitPayTime(int limitPayTime) {
		this.limitPayTime = limitPayTime;
	}

	public int getRewardType() {
		return this.rewardType;
	}

	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}

	public int getRewardIntegral() {
		return this.rewardIntegral;
	}

	public void setRewardIntegral(int rewardIntegral) {
		this.rewardIntegral = rewardIntegral;
	}

	public int getFreightSet() {
		return this.freightSet;
	}

	public void setFreightSet(int freightSet) {
		this.freightSet = freightSet;
	}

	public BigDecimal getFreight() {
		return this.freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
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