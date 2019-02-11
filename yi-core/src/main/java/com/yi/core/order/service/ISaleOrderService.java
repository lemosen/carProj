/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yi.core.gift.domain.entity.Gift;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.vo.SaleOrderListVo;
import com.yi.core.order.domain.vo.SaleOrderVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISaleOrderService {

	/**
	 * 分页查询: Order
	 * 
	 * @param query
	 * @return
	 */
	Page<SaleOrder> query(Pagination<SaleOrder> query);

	/**
	 * 分页查询: OrderListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SaleOrderListVo> queryListVo(Pagination<SaleOrder> query);

	/**
	 * 分页查询: OrderListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SaleOrderListVo> queryListVoForApp(Pagination<SaleOrder> query);

	/**
	 * 根据Entity创建Order
	 * 
	 * @param order
	 * @return
	 */
	SaleOrder addOrder(SaleOrder order);

	/**
	 * 根据BO创建Order
	 * 
	 * @param order
	 * @return
	 */
	SaleOrderVo addOrder(SaleOrderBo order);

	/**
	 * 根据Entity更新Order
	 * 
	 * @param order
	 * @return
	 */
	SaleOrder updateOrder(SaleOrder order);

	/**
	 * 根据BO更新Order
	 * 
	 * @param orderBo
	 * @return
	 */
	SaleOrderVo updateOrder(SaleOrderBo orderBo);

	/**
	 * 删除Order
	 * 
	 * @param orderId
	 */
	void removeOrderById(Integer orderId);

	/**
	 * 根据ID得到Order
	 * 
	 * @param orderId
	 * @return
	 */
	SaleOrder getOrderById(Integer orderId);

	/**
	 * 根据ID得到OrderVo
	 *
	 * @param orderId
	 * @return
	 */
	SaleOrderVo getOrderVoById(Integer orderId);

	/**
	 * 根据giftId得到OrderVo
	 *
	 * @param giftId
	 * @return
	 */
	SaleOrderVo getOrderVoByGiftId(Integer giftId);

	/**
	 * 根据ID得到OrderListVo
	 * 
	 * @param orderId
	 * @return
	 */
	SaleOrderListVo getOrderListVoById(Integer orderId);

	/**
	 * 校验活动库存
	 * 
	 * @param groupType
	 * @param groupId
	 * @param activityStock
	 * @return
	 */
	@Deprecated
	public boolean groupCheckStock(Integer groupType, Integer groupId, Integer activityStock);

	/**
	 * 修改订单状态
	 */
	void updateOrderState(Integer order, Integer state);

	/**
	 * 取消订单
	 * 
	 * @param orderId
	 * @return
	 */
	void cancelOrder(Integer orderId);

	/**
	 * 确认收货 计算积分和佣金
	 * 
	 * @param orderId
	 * @return
	 */
	void confirmReceipt(Integer orderId);

	/**
	 * 修改订单地址
	 */
	SaleOrderVo updateProvince(SaleOrderBo saleOrderBo);

	/**
	 * 修改订单金额
	 */
	SaleOrderVo updatePrice(SaleOrderBo saleOrderBo);

	/**
	 * 发货
	 * 
	 * @param id
	 * @param trackingNo
	 * @param logisticsCompany
	 * @return
	 */
	SaleOrderVo delivery(Integer id, String trackingNo, String logisticsCompany);

	/**
	 * 根据时间 统计订单数
	 * 
	 * @param date
	 * @return
	 */
	int getOrderNumByDate(Date date);

	/**
	 * 根据时间 统计供应商订单数
	 *
	 * @param date
	 * @return
	 */
	int getOrderNumByDateAndSupplier(Date date, Integer supplierId);

	/**
	 * 获取 待发货订单数
	 * 
	 * @return
	 */
	int getWaitDeliveryNum();

	/**
	 * 获取供应商待发货订单数
	 *
	 * @return
	 */
	int getWaitDeliveryNumSupplier(Integer supplierId);

	/**
	 * 根据日期 统计交易金额
	 * 
	 * @param date
	 * @return
	 */
	BigDecimal getTradeAmountByDate(Date date, Integer[] state);

	/**
	 * 根据日期 统计供应商交易金额
	 *
	 * @param date
	 * @return
	 */
	BigDecimal getTradeAmountByDateAndSupplier(Date date, Integer[] state, Integer supplierId);

	/**
	 * 统计 售后订单数量
	 * 
	 * @return
	 */
	int getAfterSaleOrderNum();

	/**
	 * 统计 供应商售后订单数量
	 *
	 * @return
	 */
	int getAfterSaleOrderNumBySupplier(Integer supplierId);

	/**
	 * 获取会员消费排行
	 * 
	 * @return
	 */
	List<Object[]> getMemberConsumesByDate(Pageable pageable, Date startDate, Date endDate, Integer[] state);

	/**
	 * 获取商品销量排行
	 * 
	 * @return
	 */
	List<Object[]> getCommoditySalesByDate(Pageable pageable, Date startDate, Date endDate, Integer[] state);

	/**
	 * 供应商获取会员消费排行
	 *
	 * @return
	 */
	List<Object[]> getMemberConsumesByDateAndSupplier(Pageable pageable, Date startDate, Date endDate, Integer[] state, Integer supplierId);

	/**
	 * 供应商获取商品销量排行
	 *
	 * @return
	 */
	List<Object[]> getCommoditySalesByDateAndSupplier(Pageable pageable, Date startDate, Date endDate, Integer[] state, Integer supplierId);

	/**
	 * 统计每天新增数量
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getDailyAddNumByDate(Date startDate, Date endDate);

	/**
	 * 统计供应商每天新增数量
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getDailyAddNumByDateAndSupplierId(Date startDate, Date endDate, Integer supplierId);

	/**
	 * 根据状态 统计订单数
	 *
	 * @param deleted
	 * @param state
	 * @return
	 */
	int countByDeletedAndState(Integer deleted, Integer state);

	/**
	 * 供应商业绩排名
	 */
	List<Object[]> getSupplierAchievements(Pageable pageable, Date startDate, Date endDate, Integer[] state);

	/**
	 * 获取需要支付的订单
	 * 
	 * @param ids
	 * @return
	 */
	List<SaleOrder> getPayOrderByIds(String ids);

	/**
	 * 支付成功以后 调用方法
	 * 
	 * @param resultMap
	 */
	void afterPayByWeChat(SortedMap<String, String> resultMap);

	/**
	 * 用户订单状态数量
	 * 
	 * @param memberId
	 * @return
	 */
	int[] getOrderState(Integer memberId);

	/**
	 * 微信获取待支付的订单
	 * 
	 * @return
	 */
	List<SaleOrder> getWaitPayOrdersForWeChat();

	/**
	 * 评论时 修改订单
	 * 
	 * @param orderId
	 */
	void updateSaleOrderByComment(Integer orderId);

	/**
	 * 全国拼团提交订单
	 */
	@Deprecated
	SaleOrderVo groupOrder(SaleOrderBo saleOrderBo);

	/**
	 * 确认订单 -拼团
	 *
	 * @param saleOrderBo
	 * @return
	 */
	@Deprecated
	SaleOrderVo confirmGroupOrder(SaleOrderBo saleOrderBo);

	/**
	 * 提交订单 -拼团
	 *
	 * @param saleOrderBo
	 * @return
	 */
	@Deprecated
	SaleOrderVo submitGroupOrder(SaleOrderBo saleOrderBo);

	/**
	 * 活动订单状态
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	void groupState(Integer id);

	/**
	 * 确认订单
	 * 
	 * @param saleOrderBo
	 * @return
	 */
	SaleOrderVo confirmOrder(SaleOrderBo saleOrderBo);

	/**
	 * 提交订单
	 * 
	 * @param saleOrderBo
	 * @return
	 */
	SaleOrderVo submitOrder(SaleOrderBo saleOrderBo);

	/**
	 * 自动关闭未付款的订单
	 */
	void closeOrderByTask() throws Exception;

	/**
	 * 自动收货
	 */
	void autoReceiptByTask() throws Exception;

	/**
	 * 自动完成交易
	 */
	void autoTradeByTask() throws Exception;

	/**
	 * 自动评论
	 */
	void autoCommentByTask() throws Exception;

	/**
	 * 支付宝 获取待支付的订单
	 * 
	 * @return
	 */
	List<SaleOrder> getWaitPayOrdersForAlipay(String payOrderNo);

	/**
	 * 通过礼包 创建礼包订单
	 **/
	SaleOrder addGiftOrderByGiftBag(GiftBag giftBag);

	/**
	 * 通过礼物 创建礼物订单
	 **/
	SaleOrder addGiftOrderByGift(Gift gift);

	/**
	 * 获取礼包的订单
	 * 
	 * @param giftBagId
	 * @return
	 */
	SaleOrder getByGiftBag(Integer giftBagId);

	/**
	 * 退款时 关闭销售订单
	 * 
	 * @param saleOrder
	 */
	void closeOrderByRefund(SaleOrder saleOrder);

}
