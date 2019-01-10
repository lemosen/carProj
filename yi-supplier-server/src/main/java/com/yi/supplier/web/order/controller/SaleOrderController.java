/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.vo.SaleOrderListVo;
import com.yi.core.order.domain.vo.SaleOrderVo;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.supplier.web.auth.jwt.JwtSupplierToken;
import com.yi.supplier.web.auth.jwt.SupplierToken;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 销售订单
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/saleOrder")
public class SaleOrderController {

	private final Logger LOG = LoggerFactory.getLogger(SaleOrderController.class);

	@Resource
	private ISaleOrderService orderService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SaleOrderListVo> queryOrder(@RequestBody Pagination<SaleOrder> query, HttpServletRequest request) {
		SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
		query.getParams().put("supplier.id", supplierToken.getId());
		Page<SaleOrderListVo> page = orderService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewOrder(@RequestParam("id") int orderId) {
		try {
			SaleOrderVo order = orderService.getOrderVoById(orderId);
			return RestUtils.successWhenNotNull(order);
		} catch (BusinessException ex) {
			LOG.error("get Order failure : id=orderId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addOrder(@RequestBody SaleOrderBo order) {
		try {
			SaleOrderVo dbOrder = orderService.addOrder(order);
			return RestUtils.successWhenNotNull(dbOrder);
		} catch (BusinessException ex) {
			LOG.error("add Order failure : order", order, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateOrder(@RequestBody SaleOrderBo order) {
		try {
			SaleOrderVo dbOrder = orderService.updateOrder(order);
			return RestUtils.successWhenNotNull(dbOrder);
		} catch (BusinessException ex) {
			LOG.error("update Order failure : order", order, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeOrderById(@RequestParam("id") int orderId) {
		try {
			orderService.removeOrderById(orderId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Order failure : id=orderId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 修改订单状态
	 */
	@RequestMapping(value = "updateState", method = RequestMethod.GET)
	public RestResult updateOrderState(@RequestParam("id") int id, @RequestParam("state") Integer state) {

		try {
			orderService.updateOrderState(id, state);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 发货
	 */
	@RequestMapping(value = "delivery", method = RequestMethod.GET)
	public RestResult delivery(@RequestParam("id") int id, @RequestParam("trackingNo") String trackingNo, @RequestParam("logisticsCompany") String logisticsCompany) {
		try {
			return RestUtils.success(orderService.delivery(id, trackingNo, logisticsCompany));
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}