/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.vo.SupplierListVo;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.validator.constraints.Length;

import com.yihz.common.convert.domain.ListVoDomain;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SupplierCheckAccountListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 供应商对账ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 供应商（supplier表ID）
	 */
	@NotNull
	@Max(9999999999L)
	private int supplierId;
	/**
	 * 供应商名称
	 */
	@Length(max = 32)
	private String supplierName;
	/**
	 * 订单（sale_order表ID）
	 */
	@NotNull
	@Max(9999999999L)
	private int saleOrderId;
	/**
	 * 订单编号
	 */
	@Length(max = 16)
	private String saleOrderNo;
	/**
	 * 下单时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date orderTime;
	/**
	 * 商品（product表ID）
	 */
	@NotNull
	@Max(9999999999L)
	private int productId;
	/**
	 * 商品编号
	 */
	@Length(max = 127)
	private String productNo;
	/**
	 * 商品名称
	 */
	@Length(max = 127)
	private String productName;
	/**
	 * 供货价
	 */
	@NotNull
	@Max(999999999999999L)
	private BigDecimal supplyPrice;
	/**
	 * 数量
	 */
	@NotNull
	@Max(9999999999L)
	private int quantity;
	/**
	 * 应结货款
	 */
	@NotNull
	@Max(999999999999999L)
	private BigDecimal settlementAmount;
	/**
	 * 结算时间
	 */
	@NotNull
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date settlementTime;
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

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public int getSaleOrderId() {
		return this.saleOrderId;
	}

	public void setSaleOrderId(int saleOrderId) {
		this.saleOrderId = saleOrderId;
	}

	public String getSaleOrderNo() {
		return this.saleOrderNo;
	}

	public void setSaleOrderNo(String saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getSupplyPrice() {
		return this.supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Date getSettlementTime() {
		return this.settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

}