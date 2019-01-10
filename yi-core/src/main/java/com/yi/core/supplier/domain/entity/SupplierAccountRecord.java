/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */


package com.yi.core.supplier.domain.entity;

import java.io.*;
import java.net.*;
import java.util.*;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * *
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SupplierAccountRecord implements Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 账户记录ID
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
    private Supplier supplier;
    /**
     * 操作类型（1收入2支出）
     */
    @Max(127)
    private int operateType;
    /**
     * 流水号
     */
    @Length(max = 16)
    private String serialNo;
    /**
     * 交易金额
     */
    @Max(999999999999999L)
    private Integer tradeAmount;
    /**
     * 账户余额
     */
    @Max(999999999999999L)
    private Integer balance;
    /**
     * 交易类型（1订单收入2提现3退款）
     */
    @Max(127)
    private int tradeType;
    /**
     * 交易时间
     */

    private Date tradeTime;
    /**
     * 备注
     */
    @Length(max = 127)
    private String remark;
    //columns END


    public SupplierAccountRecord() {
    }

    public SupplierAccountRecord(
            int id
    ) {
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

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
    public int getOperateType() {
        return this.operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
    public String getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
    public Integer getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Integer tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
    public int getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getTradeTime() {
        return this.tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
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
    @JoinColumns({
            @JoinColumn(name = "SUPPLIER_ID", nullable = false, insertable = false, updatable = false)
    })
    public Supplier getSupplier() {
        return supplier;
    }


}