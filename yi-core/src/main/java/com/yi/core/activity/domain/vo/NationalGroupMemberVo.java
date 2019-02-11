/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */


package com.yi.core.activity.domain.vo;

import java.io.*;
import java.net.*;
import java.util.*;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.simple.NationalGroupRecordSimple;
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
public class NationalGroupMemberVo extends VoDomain implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 全国拼团成员ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * 全国拼团记录（national_group_record表ID）
     */
    @NotNull
    private NationalGroupRecordSimple nationalGroupRecord;
    /**
     * 成员（member表ID）
     */
    @NotNull
    private MemberSimple member;
    /**
     * 收货人
     */
    @Length(max = 16)
    private String consignee;
    /**
     * 收货人电话
     */
    @Length(max = 16)
    private String consigneePhone;
    /**
     * 收货人地址
     */
    @Length(max = 127)
    private String consigneeAddr;
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date createTime;

    /**
     * 是否支付 0否 1是
     */
    @NotNull
    @Max(127)
    private int pay;

    //columns END

    /**
     * 状态（1等待开团2开团成功3开团失败）
     */
    private Integer state;

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

    public NationalGroupRecordSimple getNationalGroupRecord() {
        return nationalGroupRecord;
    }

    public void setNationalGroupRecord(NationalGroupRecordSimple nationalGroupRecord) {
        this.nationalGroupRecord = nationalGroupRecord;
    }

    public MemberSimple getMember() {
        return member;
    }

    public void setMember(MemberSimple member) {
        this.member = member;
    }

    public String getConsignee() {
        return this.consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneePhone() {
        return this.consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeAddr() {
        return this.consigneeAddr;
    }

    public void setConsigneeAddr(String consigneeAddr) {
        this.consigneeAddr = consigneeAddr;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}