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
import com.yi.core.activity.domain.simple.RebateGroupSimple;
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
public class RebateSetVo extends VoDomain implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 返现设置ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * 返现拼团（rebate_group表ID）
     */
    @NotNull
    private RebateGroupSimple rebateGroup;
    /**
     * 拼团人数
     */
    @Max(9999999999L)
    private int groupPeople;
    /**
     * 拼团价
     */

    private BigDecimal groupPrice;
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

    public RebateGroupSimple getRebateGroup() {
        return rebateGroup;
    }

    public void setRebateGroup(RebateGroupSimple rebateGroup) {
        this.rebateGroup = rebateGroup;
    }

    public int getGroupPeople() {
        return this.groupPeople;
    }

    public void setGroupPeople(int groupPeople) {
        this.groupPeople = groupPeople;
    }

    public BigDecimal getGroupPrice() {
        return this.groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}