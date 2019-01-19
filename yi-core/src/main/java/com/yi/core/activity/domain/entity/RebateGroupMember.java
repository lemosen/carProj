/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */
package com.yi.core.activity.domain.entity;


import static javax.persistence.GenerationType.IDENTITY;

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
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Deprecated
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class RebateGroupMember implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 返现拼团成员ID
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
    private RebateRecord rebateGroupRecord;
    /**
     * 成员（member表ID）
     */
    @NotNull
    private Member member;
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

    private Date createTime;
    //columns END


    public RebateGroupMember() {
    }

    public RebateGroupMember(
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


    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
    public String getConsignee() {
        return this.consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
    public String getConsigneePhone() {
        return this.consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
    public String getConsigneeAddr() {
        return this.consigneeAddr;
    }

    public void setConsigneeAddr(String consigneeAddr) {
        this.consigneeAddr = consigneeAddr;
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


    public void setMember(Member member) {
        this.member = member;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "MEMBER_ID", nullable = false)
    })
    public Member getMember() {
        return member;
    }


    public void setRebateGroupRecord(RebateRecord rebateGroupRecord) {
        this.rebateGroupRecord = rebateGroupRecord;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "REBATE_GROUP_RECORD_ID", nullable = false)
    })
    public RebateRecord getRebateGroupRecord() {
        return rebateGroupRecord;
    }


}