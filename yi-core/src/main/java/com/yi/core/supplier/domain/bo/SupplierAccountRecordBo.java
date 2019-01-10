/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */


package com.yi.core.supplier.domain.bo;

import java.io.*;
import java.net.*;
import java.util.*;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import com.yihz.common.convert.domain.BoDomain;

import javax.validation.constraints.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * *
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class SupplierAccountRecordBo extends BoDomain implements Serializable {

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
    private SupplierBo supplier;
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
    @JsonDeserialize(using = JsonTimestampDeserializer.class)
    private Date tradeTime;
    /**
     * 备注
     */
    @Length(max = 127)
    private String remark;
    //columns END

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

    public SupplierBo getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierBo supplier) {
        this.supplier = supplier;
    }

    public int getOperateType() {
        return this.operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Integer tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public int getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public Date getTradeTime() {
        return this.tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}