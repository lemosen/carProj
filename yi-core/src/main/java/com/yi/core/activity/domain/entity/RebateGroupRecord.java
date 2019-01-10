/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */
package com.yi.core.activity.domain.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class RebateGroupRecord implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 返现拼团记录ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * 团编号
     */
    @Length(max = 127)
    private String groupCode;
    /**
     * 返现拼团（rebate_group表ID）
     */
    @NotNull
    private RebateGroup rebateGroup;
    /**
     * 团长（member表ID）
     */
    @NotNull
    private Member member;
    /**
     * 团长拼团价（退款时根据当前拼团价退款）
     */
    @NotNull
    private BigDecimal groupPrice;
    /**
     * 参团人数
     */
    @NotNull
    @Max(9999999999L)
    private int joinPeople;
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
     * 状态（0成功）
     */
    @NotNull
    @Max(127)
    private int state;
    /**
     * 创建时间
     */

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

    private Date delTime;
    //columns END


    public RebateGroupRecord() {
    }

    public RebateGroupRecord(
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

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }


    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
    public BigDecimal getGroupPrice() {
        return this.groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
    public int getJoinPeople() {
        return this.joinPeople;
    }

    public void setJoinPeople(int joinPeople) {
        this.joinPeople = joinPeople;
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

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
    public int getDeleted() {
        return this.deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getDelTime() {
        return this.delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }


    private Set<RebateGroupMember> rebateGroupMembers;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "rebateGroupRecord", orphanRemoval = true)
    public Set<RebateGroupMember> getRebateGroupMembers() {
        return rebateGroupMembers;
    }

    public void setRebateGroupMembers(Set<RebateGroupMember> rebateGroupMember) {
        this.rebateGroupMembers = rebateGroupMember;
    }

    private Set<RebateRecord> rebateRecords;
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "rebateGroupRecord", orphanRemoval = true)
    public Set<RebateRecord> getRebateRecords() {
        return rebateRecords;
    }

    public void setRebateRecords(Set<RebateRecord> rebateRecords) {
        this.rebateRecords = rebateRecords;
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


    public void setRebateGroup(RebateGroup rebateGroup) {
        this.rebateGroup = rebateGroup;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "REBATE_GROUP_ID", nullable = false)
    })
    public RebateGroup getRebateGroup() {
        return rebateGroup;
    }


}