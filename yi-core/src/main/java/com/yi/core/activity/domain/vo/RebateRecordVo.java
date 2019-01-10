/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */


package com.yi.core.activity.domain.vo;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.simple.RebateGroupRecordSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import static javax.persistence.GenerationType.IDENTITY;

import com.yihz.common.convert.domain.VoDomain;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Deprecated
public class RebateRecordVo extends VoDomain implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 返现记录ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * 返现拼团记录（rebate_group_record表ID）
     */
    @NotNull
    private RebateGroupRecordSimple rebateGroupRecord;
    /**
     * 会员（member表ID）
     */
    @NotNull
    private MemberSimple member;
    /**
     * 拼团价-冗余
     */

    private BigDecimal groupPrice;
    /**
     * 返现金额
     */
    @NotNull
    private BigDecimal rebateAmount;
    /**
     * 状态（0成功）
     */
    @NotNull
    @Max(127)
    private int state;
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date createTime;
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

    public RebateGroupRecordSimple getRebateGroupRecord() {
        return rebateGroupRecord;
    }

    public void setRebateGroupRecord(RebateGroupRecordSimple rebateGroupRecord) {
        this.rebateGroupRecord = rebateGroupRecord;
    }

    public MemberSimple getMember() {
        return member;
    }

    public void setMember(MemberSimple member) {
        this.member = member;
    }

    public BigDecimal getGroupPrice() {
        return this.groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    public BigDecimal getRebateAmount() {
        return this.rebateAmount;
    }

    public void setRebateAmount(BigDecimal rebateAmount) {
        this.rebateAmount = rebateAmount;
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


}