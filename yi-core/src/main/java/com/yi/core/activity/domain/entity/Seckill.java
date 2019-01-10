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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Product;
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
public class Seckill implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 秒杀活动ID
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

    private Date startTime;
    /**
     * 结束时间
     */

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
    private Product product;
    /**
     * 活动库存
     */
    @NotNull
    @Max(9999999999L)
    private int activityStock;
    /**
     * 秒杀价
     */
    @NotNull
    private BigDecimal seckillPrice;
    /**
     * 限购数量
     */
    @NotNull
    @Max(9999999999L)
    private int limitQuantity;
    /**
     * 支付时限（单位分钟），XX分钟内不支付，自动释放库存
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
    private Coupon coupon;
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


    public Seckill() {
    }

    public Seckill(
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

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 255)
    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getActivityCover() {
        return this.activityCover;
    }

    public void setActivityCover(String activityCover) {
        this.activityCover = activityCover;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getShareTitle() {
        return this.shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
    public int getActivityStock() {
        return this.activityStock;
    }

    public void setActivityStock(int activityStock) {
        this.activityStock = activityStock;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
    public BigDecimal getSeckillPrice() {
        return this.seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
    public int getLimitQuantity() {
        return this.limitQuantity;
    }

    public void setLimitQuantity(int limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
    public int getLimitPayTime() {
        return this.limitPayTime;
    }

    public void setLimitPayTime(int limitPayTime) {
        this.limitPayTime = limitPayTime;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
    public int getRewardType() {
        return this.rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public int getRewardIntegral() {
        return this.rewardIntegral;
    }

    public void setRewardIntegral(int rewardIntegral) {
        this.rewardIntegral = rewardIntegral;
    }


    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
    public int getFreightSet() {
        return this.freightSet;
    }

    public void setFreightSet(int freightSet) {
        this.freightSet = freightSet;
    }

    @Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
    public BigDecimal getFreight() {
        return this.freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
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


    private Set<SeckillRecord> seckillRecords;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "seckill", orphanRemoval = true)
    public Set<SeckillRecord> getSeckillRecords() {
        return seckillRecords;
    }

    public void setSeckillRecords(Set<SeckillRecord> seckillRecords) {
        this.seckillRecords = seckillRecords;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "COUPON_ID", nullable = true, insertable = true, updatable = true)
    })
    public Coupon getCoupon() {
        return coupon;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "PRODUCT_ID", nullable = true, insertable = true, updatable = true)
    })
    public Product getProduct() {
        return product;
    }


}