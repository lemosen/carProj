/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class MemberLevelVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 会员等级ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 64)
	private String guid;
	/**
	 * 等级名称
	 */
	@NotBlank
	@Length(max = 32)
	private String name;
	/**
	 * 级别(如1,2,3,4...)
	 */
	@NotNull
	private Integer rank;
	/**
	 * 会员人数（冗余）
	 */
	@NotNull
	@Max(9999999999L)
	private int quantity;
	/**
	 * 成长值满足点
	 */
	@NotNull
	@Max(9999999999L)
	private int growthValue;
	/**
	 * 折扣（0.00-100.00）
	 */
	@NotNull
	@Max(99999L)
	private BigDecimal discount;
	/**
	 * 默认等级（0非默认1默认）
	 */
	@Deprecated
	@NotNull
	private Integer initial;
	/**
	 * 默认等级（0非默认1默认）
	 */
	private Integer defaulted;
	/**
	 * 注册时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */

	private Date delTime;

	/**
	 * 邀请人数
	 */
	private Integer countNumber;
	/**
	 * 赠送红包余额
	 */
	private BigDecimal bonusBalance;
	/**
	 * 一级提成
	 */
	private BigDecimal fristRoyalty;
	/**
	 * 二级提成
	 */
	private BigDecimal secondRoyalty;
	// columns END


	public Integer getCountNumber() {
		return countNumber;
	}

	public void setCountNumber(Integer countNumber) {
		this.countNumber = countNumber;
	}

	public BigDecimal getBonusBalance() {
		return bonusBalance;
	}

	public void setBonusBalance(BigDecimal bonusBalance) {
		this.bonusBalance = bonusBalance;
	}

	public BigDecimal getFristRoyalty() {
		return fristRoyalty;
	}

	public void setFristRoyalty(BigDecimal fristRoyalty) {
		this.fristRoyalty = fristRoyalty;
	}

	public BigDecimal getSecondRoyalty() {
		return secondRoyalty;
	}

	public void setSecondRoyalty(BigDecimal secondRoyalty) {
		this.secondRoyalty = secondRoyalty;
	}

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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getGrowthValue() {
		return this.growthValue;
	}

	public void setGrowthValue(int growthValue) {
		this.growthValue = growthValue;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getInitial() {
		return this.initial;
	}

	public void setInitial(Integer initial) {
		this.initial = initial;
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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(Integer defaulted) {
		this.defaulted = defaulted;
	}

}