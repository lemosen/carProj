/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SupplierAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 账户ID
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
	private Supplier supplier;
	/**
	 * 账户金额-待定字段
	 */

	private BigDecimal amount;
	/**
	 * 账户余额
	 */

	private BigDecimal balance;
	/**
	 * 冻结金额
	 */

	private BigDecimal freezeAmount;
	/**
	 * 提现金额
	 */

	private BigDecimal withdrawAmount;
	/**
	 * 创建时间
	 */

	private Date createTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	// columns END

	public SupplierAccount() {
	}

	public SupplierAccount(int id) {
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

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getFreezeAmount() {
		return this.freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getWithdrawAmount() {
		return this.withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = false, insertable = true, updatable = true) })
	public Supplier getSupplier() {
		return supplier;
	}

}