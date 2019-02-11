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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Deprecated
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SupplierCheckAccount implements java.io.Serializable {

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
	 * 供应商名称
	 */
	@Length(max = 32)
	private String supplierName;

	/**
	 * 订单编号
	 */
	@Length(max = 16)
	private String saleOrderNo;
	/**
	 * 下单时间
	 */

	private Date orderTime;

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
	private Date settlementTime;
	// columns END

	public SupplierCheckAccount() {
	}

	public SupplierCheckAccount(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getSaleOrderNo() {
		return this.saleOrderNo;
	}

	public void setSaleOrderNo(String saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}


	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
	public BigDecimal getSupplyPrice() {
		return this.supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
	public BigDecimal getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getSettlementTime() {
		return this.settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	private SaleOrder saleOrder;

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SALE_ORDER_ID", nullable = false) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	private Product product;

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "PRODUCT_ID", nullable = false) })
	public Product getProduct() {
		return product;
	}

	private Supplier supplier;

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = false) })
	public Supplier getSupplier() {
		return supplier;
	}

}