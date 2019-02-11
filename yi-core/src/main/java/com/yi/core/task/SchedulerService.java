package com.yi.core.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.common.SmsService;
import com.yi.core.gift.service.IGiftBagService;
import com.yi.core.gift.service.IGiftService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yi.core.order.service.ISaleOrderService;

/**
 * 定时调度任务
 * 
 * @author xuyh
 *
 */
@Component
public class SchedulerService {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

	@Resource
	private SmsService smsService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IGiftBagService giftBagService;

	@Resource
	private IGiftService giftService;

	@Resource
	private IMemberCommissionService memberCommissionService;

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	/**
	 * 短信回执 每小时执行一次
	 * 
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void smsReceiptTask() {
		try {
			smsService.updateSmsSendState();
		} catch (Exception e) {
			LOG.error("短信回执-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动作废优惠券 </br>
	 * 1分钟执行一次
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void autoCancelCouponTask() {
		try {
			couponReceiveService.autoCancelCouponByTask();
		} catch (Exception e) {
			LOG.error("自动作废优惠券-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动关闭未付款订单 每1分钟执行一次</br>
	 * 正常订单超过 XX 分钟未付款，订单自动关闭
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void closeOrderTask() {
		try {
			saleOrderService.closeOrderByTask();
		} catch (Exception e) {
			LOG.error("自动关闭未付款订单-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动收货 每小时执行一次</br>
	 * 发货超过 XX 天未收货，订单自动完成
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoReceiptTask() {
		try {
			saleOrderService.autoReceiptByTask();
		} catch (Exception e) {
			LOG.error("自动收货-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动完成交易 每小时执行一次 </br>
	 * 正常完成超过 XX 天 自动完成交易，不能申请售后
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoTradeTask() {
		try {
			saleOrderService.autoTradeByTask();
		} catch (Exception e) {
			LOG.error("自动完成交易-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动评论 每小时执行一次</br>
	 * 正常完成超过 XX 天 自动五星好评
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoCommentTask() {
		try {
			saleOrderService.autoCommentByTask();
		} catch (Exception e) {
			LOG.error("自动评价-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 自动作废礼包</br>
	 * 1分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void autoCancelGiftBagTask() {
		try {
			giftBagService.autoCancelGiftBagByTask();
		} catch (Exception e) {
			LOG.error("自动作废礼包-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 自动作废礼物</br>
	 * 1分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void autoCancelGiftTask() {
		try {
			giftService.autoCancelGiftByTask();
		} catch (Exception e) {
			LOG.error("自动作废礼物-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 自动将未结算佣金转到可提现佣金</br>
	 * 1小时执行一次
	 * 
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoUnsettledCommissionToCashableCommissionTask() {
		try {
			memberCommissionService.autoUnsettledCommissionToCashableCommissionByTask();
		} catch (Exception e) {
			LOG.error("自动将未计算的佣金转到可提现佣金-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 退款回执 </br>
	 * 3分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */3 * * * ?")
	public void refundReceiptTask() {
		try {
			afterSaleOrderService.refundReceiptByTask();
		} catch (Exception e) {
			LOG.error("退款回执-->执行异常{}", e.getMessage(), e);
		}
	}

}
