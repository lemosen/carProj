/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonDateDeserializer;
import com.yihz.common.json.serializer.JsonDateSerializer;

/**
 * 供应商销售统计
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SupplierSaleStats implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 供应商销售统计ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 供应商订单数
	 */
	private int supplierOrderNum;
	/**
	 * 销售额
	 */
	private BigDecimal saleAmount;
	/**
	 * 供应商占比
	 */
	private BigDecimal supplierRatio;
	/**
	 * 成本
	 */
	private BigDecimal cost;
	/**
	 * 利润
	 */
	private BigDecimal profit;
	/**
	 * 统计时间
	 */
	private Date statTime;
	// columns END

	public SupplierSaleStats() {
	}

	public SupplierSaleStats(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, length = 10)
	public int getSupplierOrderNum() {
		return this.supplierOrderNum;
	}

	public void setSupplierOrderNum(int supplierOrderNum) {
		this.supplierOrderNum = supplierOrderNum;
	}

	@Column(unique = false, nullable = false, length = 15)
	public BigDecimal getSaleAmount() {
		return this.saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	@Column(unique = false, nullable = false, length = 15)
	public BigDecimal getSupplierRatio() {
		return this.supplierRatio;
	}

	public void setSupplierRatio(BigDecimal supplierRatio) {
		this.supplierRatio = supplierRatio;
	}

	@Column(unique = false, nullable = false, length = 15)
	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(unique = false, nullable = false, length = 15)
	public BigDecimal getProfit() {
		return this.profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getStatTime() {
		return this.statTime;
	}

	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

}