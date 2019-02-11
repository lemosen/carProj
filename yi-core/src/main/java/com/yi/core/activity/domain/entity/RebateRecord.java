/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */
package com.yi.core.activity.domain.entity;


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
public class RebateRecord implements java.io.Serializable {

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
    private RebateGroupRecord rebateGroupRecord;
    /**
     * 会员（member表ID）
     */
    @NotNull
    private Member member;

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

    private Date createTime;
    //columns END


    public RebateRecord() {
    }

    public RebateRecord(
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

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
    public BigDecimal getGroupPrice() {
        return this.groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
    public BigDecimal getRebateAmount() {
        return this.rebateAmount;
    }

    public void setRebateAmount(BigDecimal rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
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

    public void setRebateGroupRecord(RebateGroupRecord rebateGroupRecord) {
        this.rebateGroupRecord = rebateGroupRecord;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "REBATE_GROUP_RECORD_ID", nullable = false)
    })
    public RebateGroupRecord getRebateGroupRecord() {
        return rebateGroupRecord;
    }


}