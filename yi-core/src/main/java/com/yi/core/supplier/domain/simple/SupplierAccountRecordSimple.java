/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */


package com.yi.core.supplier.domain.simple;

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
import com.yihz.common.convert.domain.SimpleDomain;

import static javax.persistence.GenerationType.IDENTITY;

/**
*  *
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
*/
public class SupplierAccountRecordSimple  extends SimpleDomain implements Serializable {

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
            @Length(max=32)
    private String guid;
    /**
    * 供应商（supplier表ID）
    */
            @NotNull @Max(9999999999L)
    private int supplierId;
    /**
    * 操作类型（1收入2支出）
    */
            @Max(127)
    private int operateType;
    /**
    * 流水号
    */
            @Length(max=16)
    private String serialNo;
    /**
    * 交易金额
    */
            @Max(999999999999999L)
    private long tradeAmount;
    /**
    * 账户余额
    */
            @Max(999999999999999L)
    private long balance;
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
            @Length(max=127)
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

        public int getSupplierId() {
            return this.supplierId;
        }

        public void setSupplierId(int supplierId) {
            this.supplierId = supplierId;
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

        public long getTradeAmount() {
            return this.tradeAmount;
        }

        public void setTradeAmount(long tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public long getBalance() {
            return this.balance;
        }

        public void setBalance(long balance) {
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