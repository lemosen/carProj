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
import com.yi.core.activity.domain.simple.SeckillSimple;
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
public class SeckillRecordVo extends VoDomain implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 秒杀活动记录ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * 秒杀活动（seckill表ID）
     */
    @NotNull
    private SeckillSimple seckill;
    /**
     * 会员（member表ID）
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
     * 状态（1成功2失败）
     */
    @NotNull
    @Max(127)
    private int state;
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date createTime;
    /**
     * 删除（0否1是）
     */
    @NotNull
    @Max(127)
    private int deleted;
    /**
     * 删除时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date delTime;
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

    public SeckillSimple getSeckill() {
        return seckill;
    }

    public void setSeckill(SeckillSimple seckill) {
        this.seckill = seckill;
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

    public int getDeleted() {
        return this.deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Date getDelTime() {
        return this.delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }


}