//package com.yi.core.payment.alipay;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.alipay.api.internal.util.AlipaySignature;
//import com.yi.core.commodity.service.IStockRecordService;
//import com.yi.core.commodity.service.IStockService;
//import com.yi.core.member.MemberEnum;
//import com.yi.core.member.service.IAccountRecordService;
//import com.yi.core.member.service.IMemberService;
//import com.yi.core.order.OrderEnum;
//import com.yi.core.order.domain.entity.SaleOrder;
//import com.yi.core.order.service.IOrderLogService;
//import com.yi.core.order.service.IPayRecordService;
//import com.yi.core.order.service.ISaleOrderService;
//import com.yihz.common.utils.date.DateUtils;
//
///**
// * 支付宝 回调服务
// * 
// * @author xuyh
// *
// */
//@Component
//public class AlipayNotifyService {
//
//	private static final Logger LOG = LoggerFactory.getLogger(AlipayNotifyService.class);
//
//	@Resource
//	private IMemberService memberService;
//
//	@Resource
//	private ISaleOrderService saleOrderService;
//
//	@Resource
//	private IOrderLogService orderLogService;
//
//	@Resource
//	private IPayRecordService payRecordService;
//
//	@Resource
//	private IStockService stockService;
//
//	@Resource
//	private IStockRecordService stockRecordService;
//
//	@Resource
//	private IAccountRecordService accountRecordService;
//
//	/**
//	 * 支付宝回调接口
//	 * 
//	 * @param params
//	 */
//	public String alipayNotify(Map<String, String> resultMap) throws Exception {
//		if (MapUtils.isEmpty(resultMap)) {
//			LOG.error("支付宝回调，参数为空");
//			return AlipayConfig.NOTIFY_FAIL;
//		}
//		// 校验签名
//		boolean signVerfied = AlipaySignature.rsaCheckV1(resultMap, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET_UTF8, AlipayConfig.SIGN_TYPE);
//		if (signVerfied) {
//			// 验签成功+交易成功
//			if (AlipayConfig.TRADE_STATUS_SUCCESS.equals(resultMap.get("trade_status"))) {
//				/**
//				 * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号</br>
//				 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）</br>
//				 * 3、校验通知中的seller_id（或者seller_email)，是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），</br>
//				 * 4、验证app_id是否为该商户本身。
//				 */
//				// if (!AlipayConfig.APP_ID.equals(params.get("app_id"))) {
//				// LOG.error("支付宝回调失败，商户app_id不一致");
//				// return AlipayConfig.NOTIFY_FAIL;
//				// }
//				// String out_trade_no = params.get("out_trade_no");
//				// String total_amount = params.get("total_amount");
//				// String seller_id = params.get("seller_id");
//				// String app_id = params.get("app_id");
//
//				List<SaleOrder> dbSaleOrders = saleOrderService.getWaitPayOrdersForAlipay(resultMap.get("out_trade_no"));
//				if (CollectionUtils.isEmpty(dbSaleOrders)) {
//					LOG.error("支付宝回调失败，根据out_trade_no查询数据为空");
//					return AlipayConfig.NOTIFY_FAIL;
//				}
//				// 计算订单金额
//				BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
//				if (!payAmount.toString().equals(resultMap.get("total_amount"))) {
//					LOG.error("支付宝回调失败，支付金额{}和后台订单金额不一致", resultMap.get("total_amount"), payAmount);
//					return AlipayConfig.NOTIFY_FAIL;
//				}
//
//				for (SaleOrder tmpOrder : dbSaleOrders) {
//					if (tmpOrder != null && OrderEnum.STATE_WAIT_PAY.getCode().equals(tmpOrder.getState())) {
//						// 更新订单状态
//						tmpOrder.setState(OrderEnum.STATE_WAIT_DELIVERY.getCode());// 订单状态 待发货
//						tmpOrder.setPaymentTime(DateUtils.parseDate(resultMap.get("gmt_payment"), "yyyy-MM-dd HH:mm:ss"));
//						tmpOrder.setTradeNo(resultMap.get("trade_no"));// 交易号
//						tmpOrder.setPayMode(OrderEnum.PAY_MODE_ALIPAY.getCode());
//						tmpOrder.setRemark("支付完成");
//						// 库存修正+库存记录
//						stockService.useStockByPayOrder(tmpOrder);
//						// 支付记录
//						payRecordService.addPayRecordByOrderForAlipay(tmpOrder, resultMap);
//						// 订单记录
//						orderLogService.addLogByOrder(tmpOrder, OrderEnum.ORDER_LOG_STATE_PAY_SUCCESS, "支付成功");
//						// 会员资金账户记录
//						accountRecordService.addAccountRecordByTradeType(tmpOrder.getMember(), tmpOrder.getPayAmount(), MemberEnum.TRADE_TYPE_ONLINE_PAYMENT);
//					}
//				}
//				return AlipayConfig.NOTIFY_SUCCESS;
//			} else {
//				LOG.error("支付宝回调，交易状态为" + resultMap.get("trade_status"));
//				return AlipayConfig.NOTIFY_FAIL;
//			}
//		} else {
//			// TODO 验签失败则记录异常日志，并在response中返回failure.
//			LOG.error("支付宝回调，签名校验失败");
//			return AlipayConfig.NOTIFY_FAIL;
//		}
//
//	}
//
//	/**
//	 * 根据订单 计算支付金额
//	 * 
//	 * @param saleOrders
//	 * @return
//	 */
//	private BigDecimal calculatePayAmount(List<SaleOrder> saleOrders) {
//		if (CollectionUtils.isEmpty(saleOrders)) {
//			return BigDecimal.ZERO;
//		}
//		try {
//			return saleOrders.stream().map(SaleOrder::getPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//		} catch (Exception e) {
//			LOG.error("计算总单金额异常" + e.getMessage());
//			return BigDecimal.ZERO;
//		}
//	}
//
//}
