package com.yi.webserver.web.order;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.service.ICommunityService;
import com.yi.core.commodity.domain.bo.CommentBo;
import com.yi.core.commodity.service.ICommentService;
import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.vo.SaleOrderListVo;
import com.yi.core.order.service.IOrderSettingService;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 订单
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	private final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private ICommunityService communityService;

	@Resource
	private IOrderSettingService orderSettingService;

	@Resource
	private ICommentService commentService;

	/**
	 * 分页查询根据ID与状态 获取各种状态订单类型 售后 待退货 待付款等
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "queryOrder", method = RequestMethod.POST)
	public Page<SaleOrderListVo> queryOrder(@RequestBody Pagination<SaleOrder> query) {
		Page<SaleOrderListVo> page = saleOrderService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 去结算 确认订单
	 * 
	 * @param saleOrderBo
	 * @return
	 */
	@RequestMapping(value = "confirmOrder", method = RequestMethod.POST)
	public RestResult confirmOrder(@RequestBody SaleOrderBo saleOrderBo) {
		try {
			return RestUtils.success(saleOrderService.confirmOrder(saleOrderBo));
		} catch (Exception e) {
			LOG.error("confirmOrder error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 提交订单 去支付
	 * 
	 * @param saleOrderBo
	 * @return
	 */
	@RequestMapping(value = "submitOrder", method = RequestMethod.POST)
	public RestResult submitOrder(@RequestBody SaleOrderBo saleOrderBo) {
		try {
			return RestUtils.success(saleOrderService.submitOrder(saleOrderBo));
		} catch (Exception e) {
			LOG.error("submitOrder error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 去结算 确认订单 （拼团）
	 * 
	 * @param saleOrderBo
	 * @return
	 */
	@RequestMapping(value = "confirmGroupOrder", method = RequestMethod.POST)
	public RestResult confirmGroupOrder(@RequestBody SaleOrderBo saleOrderBo) {
		if (saleOrderBo == null || CollectionUtils.isEmpty(saleOrderBo.getCartVos())) {
			return RestUtils.error("提交数据不能为空");
		}
		try {
			return RestUtils.success(saleOrderService.confirmGroupOrder(saleOrderBo));
		} catch (Exception e) {
			LOG.error("confirmGroupOrder error: " + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 提交订单 去支付（拼团）
	 * 
	 * @param saleOrderBo
	 * @return
	 */
	@RequestMapping(value = "submitGroupOrder", method = RequestMethod.POST)
	public RestResult submitGroupOrder(@RequestBody SaleOrderBo saleOrderBo) {
		if (saleOrderBo == null || CollectionUtils.isEmpty(saleOrderBo.getCartVos())) {
			return RestUtils.error("提交数据不能为空");
		}
		try {
			return RestUtils.success(saleOrderService.submitGroupOrder(saleOrderBo));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("submitGroupOrder error: " + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取订单
	 *
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "getOrder", method = RequestMethod.GET)
	public RestResult getOrder(@RequestParam("orderId") int orderId) {
		try {
			return RestUtils.success(saleOrderService.getOrderVoById(orderId));
		} catch (Exception e) {
			LOG.error("getOrder error: " + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * TODO 评论订单
	 *
	 * @param commodityComments
	 * @return
	 */
	@RequestMapping(value = "commentOrder", method = RequestMethod.POST)
	public RestResult commentOrder(@RequestBody List<CommentBo> commodityComments) {
		try {
			return RestUtils.success(commentService.commentOrder(commodityComments));
		} catch (Exception e) {
			LOG.error("commentOrder error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 关闭订单
	 *
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "cancelOrder", method = RequestMethod.GET)
	public RestResult cancelOrder(@RequestParam("orderId") int orderId) {
		try {
			saleOrderService.cancelOrder(orderId);
			return RestUtils.success(true);
		} catch (Exception e) {
			LOG.error("cancelOrder error: " + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 删除订单
	 *
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "removeOrder", method = RequestMethod.GET)
	public RestResult removeOrder(@RequestParam("orderId") int orderId) {
		try {
			saleOrderService.removeOrderById(orderId);
			return RestUtils.success(true);
		} catch (Exception e) {
			LOG.error("removeOrder error: " + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 查看小区 return_order
	 *
	 * @param communityId
	 * @return
	 */
	@RequestMapping(value = "residentialQuarters", method = RequestMethod.GET)
	public RestResult residentialQuarters(@RequestParam("communityId") int communityId) {
		try {
			return RestUtils.success(communityService.getCommunityVoById(communityId));
		} catch (Exception e) {
			LOG.error("payOrder error: " + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 确认收货
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "confirmReceipt", method = RequestMethod.GET)
	public RestResult confirmReceipt(@RequestParam("orderId") int orderId) {
		try {
			saleOrderService.confirmReceipt(orderId);
			return RestUtils.success(true);
		} catch (Exception e) {
			LOG.error("confirmReceipt error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取各种订单状态数量
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "getOrderState", method = RequestMethod.GET)
	public RestResult getOrderState(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(saleOrderService.getOrderState(memberId));
		} catch (Exception e) {
			LOG.error("getOrderState error: " + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

}
