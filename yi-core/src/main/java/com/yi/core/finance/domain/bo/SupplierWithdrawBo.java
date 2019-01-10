/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.domain.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import org.hibernate.validator.constraints.Length;

import com.yihz.common.convert.domain.BoDomain;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SupplierWithdrawBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 供应商提现ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 供应商（supplier表id）
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
	 * 提现金额
	 */
	@NotNull
	@Max(999999999999999L)
	private BigDecimal amount;
	/**
	 * 账号类型（1线下支付2..）
	 */
	@NotNull
	@Max(127)
	private int cardType;
	/**
	 * 账号/卡号/openID
	 */
	@Length(max = 127)
	private String cardNo;
	/**
	 * 收款人
	 */
	@Length(max = 32)
	private String payee;
	/**
	 * 申请状态（1待发放2发放异常）
	 */
	@NotNull
	@Max(127)
	private int state;
	/**
	 * 出错说明
	 */
	@Length(max = 255)
	private String errorDescription;
	/**
	 * 申请时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date applyTime;
	/**
	 * 发放时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date dealTime;
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

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getCardType() {
		return this.cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPayee() {
		return this.payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getErrorDescription() {
		return this.errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

}