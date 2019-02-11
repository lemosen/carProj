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
import com.yi.core.activity.domain.simple.CouponSimple;
import com.yi.core.basic.domain.simple.CommunitySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
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
public class CommunityGroupVo extends VoDomain implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 小区拼团ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * 活动标签
     */
    @NotBlank
    @Length(max = 255)
    private String label;
    /**
     * 开始时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date endTime;
    /**
     * 活动封面
     */
    @Length(max = 255)
    private String activityCover;
    /**
     * 分享标题
     */
    @Length(max = 255)
    private String shareTitle;
    /**
     * 商品（product表ID）
     */
    @NotNull
    private ProductSimple product;
    /**
     * 小区（community表ID）
     */
    @NotNull
    private CommunitySimple community;
    /**
     * 活动库存
     */
    @NotNull
    @Max(9999999999L)
    private int activityStock;
    /**
     * 拼团价
     */
    @NotNull
    private BigDecimal groupPrice;
    /**
     * 成团人数
     */
    @NotNull
    @Max(9999999999L)
    private int groupPeople;
    /**
     * 成团时限（单位小时），团长发起的组团有效期
     */
    @Max(9999999999L)
    private int limitGroupTime;
    /**
     * 限购数量
     */
    @NotNull
    @Max(9999999999L)
    private int limitQuantity;
    /**
     * 支付时限（单位分钟），买家XX分钟内未支付，开团/参团记录自动取消
     */
    @NotNull
    @Max(9999999999L)
    private int limitPayTime;
    /**
     * 奖励类型（1积分2优惠券）
     */
    @NotNull
    @Max(127)
    private int rewardType;
    /**
     * 奖励积分
     */
    @Max(9999999999L)
    private int rewardIntegral;
    /**
     * 优惠券（coupon表ID）
     */
    private CouponSimple coupon;
    /**
     * 运费设置（1卖家包邮2买家承担运费）
     */
    @NotNull
    @Max(127)
    private int freightSet;
    /**
     * 运费
     */
    @NotNull
    private BigDecimal freight;
    /**
     * 状态（0启用1禁用）
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

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getActivityCover() {
        return this.activityCover;
    }

    public void setActivityCover(String activityCover) {
        this.activityCover = activityCover;
    }

    public String getShareTitle() {
        return this.shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }


    public int getActivityStock() {
        return this.activityStock;
    }

    public void setActivityStock(int activityStock) {
        this.activityStock = activityStock;
    }

    public BigDecimal getGroupPrice() {
        return this.groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    public int getGroupPeople() {
        return this.groupPeople;
    }

    public void setGroupPeople(int groupPeople) {
        this.groupPeople = groupPeople;
    }

    public int getLimitGroupTime() {
        return this.limitGroupTime;
    }

    public void setLimitGroupTime(int limitGroupTime) {
        this.limitGroupTime = limitGroupTime;
    }

    public int getLimitQuantity() {
        return this.limitQuantity;
    }

    public void setLimitQuantity(int limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public int getLimitPayTime() {
        return this.limitPayTime;
    }

    public void setLimitPayTime(int limitPayTime) {
        this.limitPayTime = limitPayTime;
    }

    public int getRewardType() {
        return this.rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public int getRewardIntegral() {
        return this.rewardIntegral;
    }

    public void setRewardIntegral(int rewardIntegral) {
        this.rewardIntegral = rewardIntegral;
    }

    public ProductSimple getProduct() {
        return product;
    }

    public void setProduct(ProductSimple product) {
        this.product = product;
    }

    public CommunitySimple getCommunity() {
        return community;
    }

    public void setCommunity(CommunitySimple community) {
        this.community = community;
    }

    public CouponSimple getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponSimple coupon) {
        this.coupon = coupon;
    }

    public int getFreightSet() {
        return this.freightSet;
    }

    public void setFreightSet(int freightSet) {
        this.freightSet = freightSet;
    }

    public BigDecimal getFreight() {
        return this.freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
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