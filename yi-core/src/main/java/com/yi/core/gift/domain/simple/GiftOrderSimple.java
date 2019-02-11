/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.domain.simple;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Deprecated
public class GiftOrderSimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 礼物订单ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberSimple member;
	/**
	 * 礼包（gift_bag表ID）
	 */
	private GiftBagSimple giftBag;
	/**
	 * 礼物（gift表ID）
	 */
	private GiftSimple gift;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierSimple supplier;
	/**
	 * 订单编号
	 */
	@Length(max = 32)
	private String orderNo;
	/**
	 * 微信/支付宝交易号
	 */
	@Length(max = 64)
	private String tradeNo;
	/**
	 * 支付方式（0未支付1支付宝2微信3银联）
	 */
	@Max(127)
	private Integer payMode;
	/**
	 * 订单类型（1送礼单2收礼单）
	 */
	@Max(127)
	private Integer orderType;
	/**
	 * 订单状态（1待付款 2已付款待发货 3已发货待收货 4已收货已完成 5已关闭已取消）
	 */
	@NotNull
	@Max(127)
	private Integer orderState;
	/**
	 * 评价状态（默认为空订单未完成 1待评价 2已评价）
	 */
	@Max(127)
	private Integer commentState;
	/**
	 * 售后状态（默认为空订单未完成 1可申请 2申请中 3已申请 4已过期）
	 */
	@Max(127)
	private Integer afterSaleState;
	/**
	 * 商品(commodity表ID)
	 */
	private CommoditySimple commodity;
	/**
	 * 货品（product表ID）
	 */
	private ProductSimple product;
	/**
	 * 单价
	 */
	@Max(999999999999999L)
	private BigDecimal price;
	/**
	 * 数量
	 */
	@Max(9999999999L)
	private int quantity;
	/**
	 * 订单金额
	 */
	@Max(999999999999999L)
	private BigDecimal orderAmount;
	/**
	 * 运费
	 */
	@Max(999999999999999L)
	private BigDecimal freight;
	/**
	 * 实付金额
	 */
	@Max(999999999999999L)
	private BigDecimal payAmount;
	/**
	 * 退款金额
	 */
	@Max(999999999999999L)
	private BigDecimal refundAmount;
	/**
	 * 买家留言
	 */
	@Length(max = 255)
	private String buyerMessage;
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
	 * 配送方式
	 */
	@Length(max = 32)
	private String deliveryMode;
	/**
	 * 物流公司
	 */
	@Length(max = 32)
	private String expressCompany;
	/**
	 * 快递单号
	 */
	@Length(max = 32)
	private String expressNo;
	/**
	 * 下单时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date orderTime;
	/**
	 * 付款时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date paymentTime;
	/**
	 * 发货时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date deliveryTime;
	/**
	 * 收货时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date receiptTime;
	/**
	 * 完成时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date finishTime;
	/**
	 * 关闭时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date closeTime;
	/**
	 * 供应商阅读状态（0未阅读1已阅读）
	 */
	@Max(127)
	private Integer readType;
	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

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

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public GiftBagSimple getGiftBag() {
		return giftBag;
	}

	public void setGiftBag(GiftBagSimple giftBag) {
		this.giftBag = giftBag;
	}

	public GiftSimple getGift() {
		return gift;
	}

	public void setGift(GiftSimple gift) {
		this.gift = gift;
	}

	public SupplierSimple getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierSimple supplier) {
		this.supplier = supplier;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getOrderState() {
		return this.orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Integer getCommentState() {
		return this.commentState;
	}

	public void setCommentState(Integer commentState) {
		this.commentState = commentState;
	}

	public Integer getAfterSaleState() {
		return this.afterSaleState;
	}

	public void setAfterSaleState(Integer afterSaleState) {
		this.afterSaleState = afterSaleState;
	}

	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public ProductSimple getProduct() {
		return product;
	}

	public void setProduct(ProductSimple product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getBuyerMessage() {
		return this.buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
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

	public String getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getExpressCompany() {
		return this.expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNo() {
		return this.expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getReceiptTime() {
		return this.receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getReadType() {
		return this.readType;
	}

	public void setReadType(Integer readType) {
		this.readType = readType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

}